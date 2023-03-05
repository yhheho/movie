package com.monstarlab.movie.controllers;

import com.monstarlab.movie.Exceptions.PageSizeTooLargeException;
import com.monstarlab.movie.Exceptions.UserNotFoundException;
import com.monstarlab.movie.models.User;
import com.monstarlab.movie.payload.response.user.GetUserResponse;
import com.monstarlab.movie.payload.response.user.ListUserResponse;
import com.monstarlab.movie.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.monstarlab.movie.Constants.Constant.PAGE_SIZE_TOO_LARGE;


@Controller
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ListUserResponse> listUsers(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) throws PageSizeTooLargeException {

        if (size > 100) {
            throw new PageSizeTooLargeException(PAGE_SIZE_TOO_LARGE);
        }
        Page<User> moviePage = userService.listUsers(PageRequest.of(page, size));
        ListUserResponse response = new ListUserResponse(moviePage.hasNext(), moviePage.getContent());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable Long id) throws UserNotFoundException {
        User user = userService.getUser(id);
        return ResponseEntity.ok(new GetUserResponse(user));
    }
}
