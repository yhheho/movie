package com.monstarlab.movie.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateFavoriteRequest {
    @NotNull
    private Long movieId;
}
