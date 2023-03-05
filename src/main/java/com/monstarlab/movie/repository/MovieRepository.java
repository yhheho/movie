package com.monstarlab.movie.repository;

import com.monstarlab.movie.models.Movie;
import com.monstarlab.movie.models.enums.MovieType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    // Define any custom query methods here
    Optional<Movie> findByTitle(String title);

    Page<Movie> findByType(MovieType type, Pageable pageable);

    @Query("SELECT m FROM Movie m WHERE LOWER(m.title) LIKE CONCAT('%', :title, '%')")
    Page<Movie> findMovieByTitle(String title, Pageable pageable);

    @Query("SELECT m FROM Movie m WHERE m.releaseDate < :startDate")
    Page<Movie> findMovieByRelease(Timestamp startDate, Pageable pageable);
}