package com.monstarlab.movie.payload.response.favorite;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.monstarlab.movie.models.Favorite;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ListFavoriteResponse {
    private boolean hasNextPage;
    private List<Favorite> favoriteList;

    @JsonCreator
    public ListFavoriteResponse() {
    }
}
