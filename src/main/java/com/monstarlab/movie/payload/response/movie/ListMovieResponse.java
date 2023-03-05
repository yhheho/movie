package com.monstarlab.movie.payload.response.movie;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.monstarlab.movie.models.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ListMovieResponse {
    private boolean hasNextPage;
    private List<Movie> movieList;

    @JsonCreator
    public ListMovieResponse() {
    }
}
