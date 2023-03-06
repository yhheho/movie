package com.monstarlab.movie.controllers;

import com.monstarlab.movie.Exceptions.FavoriteNotFoundException;
import com.monstarlab.movie.Exceptions.MovieNotFoundException;
import com.monstarlab.movie.Exceptions.PageSizeTooLargeException;
import com.monstarlab.movie.Exceptions.UserNotFoundException;
import com.monstarlab.movie.models.Favorite;
import com.monstarlab.movie.payload.request.CreateFavoriteRequest;
import com.monstarlab.movie.payload.response.favorite.CreateFavoriteResponse;
import com.monstarlab.movie.payload.response.favorite.ListFavoriteResponse;
import com.monstarlab.movie.payload.response.movie.CreateMovieResponse;
import com.monstarlab.movie.repository.FavoriteRepository;
import com.monstarlab.movie.repository.MovieRepository;
import com.monstarlab.movie.service.UserFavoriteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.monstarlab.movie.Constants.Constant.ILLEGAL_ARGUMENT;
import static com.monstarlab.movie.Constants.Constant.PAGE_SIZE_TOO_LARGE;

@Controller
@RequestMapping("/api/users")
public class UserFavoriteController {
    private static final Logger log = LoggerFactory.getLogger(UserFavoriteController.class);

    @Autowired
    private UserFavoriteService userFavoriteService;
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/{user_id}/favorites")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ListFavoriteResponse> getFavorites(@PathVariable("user_id") Long userId,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) throws PageSizeTooLargeException {
        if (size > 100) {
            throw new PageSizeTooLargeException(PAGE_SIZE_TOO_LARGE);
        }
        Page<Favorite> favoritePage = userFavoriteService.listFavorites(userId, PageRequest.of(page, size));
        ListFavoriteResponse response = new ListFavoriteResponse(favoritePage.hasNext(), favoritePage.getContent());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{user_id}/favorites")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CreateFavoriteResponse> createFavorites(@PathVariable("user_id") Long userId,
                                                                  @Valid @RequestBody CreateFavoriteRequest createFavoriteRequest,
                                                                  BindingResult bindingResult) throws MovieNotFoundException {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT);
        }
        Favorite favorite = userFavoriteService.createFavorite(userId, createFavoriteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateFavoriteResponse(favorite));
    }

    @DeleteMapping("/{user_id}/favorites/{movie_id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity deleteFavorite(@PathVariable("user_id") Long userId, @PathVariable("movie_id") Long movieId) throws MovieNotFoundException, UserNotFoundException, FavoriteNotFoundException {
        userFavoriteService.deleteFavorite(userId, movieId);
        return ResponseEntity.ok().build();
    }
}

