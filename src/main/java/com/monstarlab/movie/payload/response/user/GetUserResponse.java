package com.monstarlab.movie.payload.response.user;

import com.monstarlab.movie.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserResponse {
    private User user;
}
