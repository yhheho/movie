package com.monstarlab.movie.payload.response.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class LogoutResponse {
    private String message;
}
