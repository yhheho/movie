package com.monstarlab.movie.service;

import com.monstarlab.movie.Exceptions.UserNotFoundException;
import com.monstarlab.movie.models.User;
import com.monstarlab.movie.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.monstarlab.movie.Constants.Constant.USER_NOT_FOUND;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public Page<User> listUsers(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        List<User> users = userPage.getContent();
        if (userPage.hasNext()) {
            users.get(users.size() - 1).setHasNextPage(true);
        }
        return new PageImpl<>(users, pageable, userPage.getTotalElements());
    }

    public User getUser(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }

    public void register(User user) {
        userRepository.save(user);
    }
}
