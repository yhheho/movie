package com.monstarlab.movie.service;

import com.monstarlab.movie.Exceptions.MovieNotFoundException;
import com.monstarlab.movie.Exceptions.WrongSearchTermException;
import com.monstarlab.movie.models.Movie;
import com.monstarlab.movie.models.enums.MovieType;
import com.monstarlab.movie.payload.request.CreateMovieRequest;
import com.monstarlab.movie.payload.request.UpdateMovieRequest;
import com.monstarlab.movie.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

import static com.monstarlab.movie.Constants.Constant.MOVIE_NOT_FOUND;
import static com.monstarlab.movie.Constants.Constant.WRONG_SEARCH_TERM;

@Service
public class MovieService {
    private static final Logger log = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    private MovieRepository movieRepository;

    public Page<Movie> listMovies(Pageable pageable) {
        Page<Movie> moviePage = movieRepository.findAll(pageable);
        List<Movie> movies = moviePage.getContent();
        if (moviePage.hasNext()) {
            movies.get(movies.size() - 1).setHasNextPage(true);
        }
        return new PageImpl<>(movies, pageable, moviePage.getTotalElements());
    }

    public Page<Movie> searchMovie(String searchString, Pageable pageable) throws WrongSearchTermException {
        Page<Movie> moviePage = null;
        if (searchString.contains("type")) {
            String type = searchString.split("=")[1];
            moviePage = movieRepository.findByType(MovieType.valueOf(type), pageable);
        } else if (searchString.contains("title")) {
            String title = searchString.split("=")[1];
            moviePage = movieRepository.findMovieByTitle(title, pageable);
        } else if (searchString.contains("release_date")) {
            String startDate = searchString.split("=")[1];
            Timestamp start = Timestamp.valueOf(startDate + " 00:00:00");
            moviePage = movieRepository.findMovieByRelease(start, pageable);
        } else {
            throw new WrongSearchTermException(WRONG_SEARCH_TERM);
        }

        List<Movie> movies = moviePage.getContent();
        if (moviePage.hasNext()) {
            movies.get(movies.size() - 1).setHasNextPage(true);
        }
        return new PageImpl<>(movies, pageable, moviePage.getTotalElements());
    }

    public static Pageable getPageable(int page, int size, String sort) {
        Sort sortMethod = Sort.by("id");
        if (sort.equals("desc")) {
            sortMethod = sortMethod.descending();
        } else if (sort.equals("asc")) {
            sortMethod = sortMethod.ascending();
        }
        return PageRequest.of(page, size, sortMethod);
    }

    public Movie getMovie(Long movieId) throws MovieNotFoundException {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(MOVIE_NOT_FOUND));
    }

    @Transactional
    public Movie createMovie(CreateMovieRequest createMovieRequest) {
        Movie movie = new Movie();
        movie.setTitle(createMovieRequest.getTitle());
        movie.setDescription(createMovieRequest.getDescription());
        movie.setReleaseDate(Timestamp.valueOf(createMovieRequest.getReleaseDate() + " 00:00:00"));
        movie.setType(MovieType.valueOf(createMovieRequest.getType()));
        return movieRepository.save(movie);
    }

    @Transactional
    public Movie updateMovie(Long movieId, UpdateMovieRequest updateMovieRequest) throws MovieNotFoundException {
        Movie movie = movieRepository.findById(movieId)
                        .orElseThrow(() -> new MovieNotFoundException(MOVIE_NOT_FOUND));
        movie.setTitle(updateMovieRequest.getTitle());
        movie.setDescription(updateMovieRequest.getDescription());
        movie.setReleaseDate(Timestamp.valueOf(updateMovieRequest.getReleaseDate() + " 00:00:00"));
        movie.setType(MovieType.valueOf(updateMovieRequest.getType()));
        return movieRepository.save(movie);
    }

    @Transactional
    public void deleteMovie(Long movieId) {
        movieRepository.deleteById(movieId);
    }
}
