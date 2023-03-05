package com.monstarlab.movie.payload.response.favorite;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.monstarlab.movie.models.Favorite;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateFavoriteResponse {
    private Favorite favorite;

    @JsonCreator
    public CreateFavoriteResponse() {
    }
}
