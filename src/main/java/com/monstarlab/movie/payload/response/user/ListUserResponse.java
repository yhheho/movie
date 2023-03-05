package com.monstarlab.movie.payload.response.user;

import com.monstarlab.movie.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ListUserResponse {
    private boolean hasNextPage;

    private List<User> userList;
}
