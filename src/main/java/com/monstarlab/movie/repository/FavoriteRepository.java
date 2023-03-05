package com.monstarlab.movie.repository;

import com.monstarlab.movie.models.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Page<Favorite> findByUserId(Long id, Pageable pageable);
    void deleteByUserIdAndMovieId(Long userId, Long movieId);
}
