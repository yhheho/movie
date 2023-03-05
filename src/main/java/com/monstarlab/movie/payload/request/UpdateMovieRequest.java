package com.monstarlab.movie.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateMovieRequest {
    @NotBlank
    private String title;

    @NotNull
    private String description;

    @NotBlank
    private String releaseDate;

    @NotBlank
    private String type;
}
