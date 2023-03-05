package com.monstarlab.movie.payload.response.movie;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.monstarlab.movie.models.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateMovieResponse {
    private Movie movie;

    @JsonCreator
    public UpdateMovieResponse() {
    }
}
