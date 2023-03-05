package com.monstarlab.movie.controllers;

import com.monstarlab.movie.Exceptions.MovieNotFoundException;
import com.monstarlab.movie.Exceptions.PageSizeTooLargeException;
import com.monstarlab.movie.Exceptions.WrongSearchTermException;
import com.monstarlab.movie.models.Movie;
import com.monstarlab.movie.payload.request.CreateMovieRequest;
import com.monstarlab.movie.payload.request.UpdateMovieRequest;
import com.monstarlab.movie.payload.response.movie.CreateMovieResponse;
import com.monstarlab.movie.payload.response.movie.GetMovieResponse;
import com.monstarlab.movie.payload.response.movie.ListMovieResponse;
import com.monstarlab.movie.payload.response.movie.UpdateMovieResponse;
import com.monstarlab.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.monstarlab.movie.Constants.Constant.ILLEGAL_ARGUMENT;
import static com.monstarlab.movie.Constants.Constant.PAGE_SIZE_TOO_LARGE;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("")
    public ResponseEntity<ListMovieResponse> getMovies(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size,
                                                       @RequestParam(name = "search", defaultValue = "") String searchString,
                                                       @RequestParam(name = "sort", defaultValue = "asc") String sort) throws PageSizeTooLargeException, WrongSearchTermException {
        if (size > 100) {
            throw new PageSizeTooLargeException(PAGE_SIZE_TOO_LARGE);
        }
        Page<Movie> moviePage;
        if (searchString != null && !searchString.isEmpty()) {
            moviePage = movieService.searchMovie(searchString, MovieService.getPageable(page, size, sort));
        } else {
            moviePage = movieService.listMovies(MovieService.getPageable(page, size, sort));
        }
        ListMovieResponse response = new ListMovieResponse(moviePage.hasNext(), moviePage.getContent());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{movie_id}")
    public ResponseEntity<GetMovieResponse> getMovie(@PathVariable("movie_id") Long movieId) throws MovieNotFoundException {
        Movie movie = movieService.getMovie(movieId);
        return ResponseEntity.ok(new GetMovieResponse(movie));
    }

    @PostMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CreateMovieResponse> createMovie(@Valid @RequestBody CreateMovieRequest createMovieRequest,
                                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT);
        }
        Movie movie = movieService.createMovie(createMovieRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateMovieResponse(movie));
    }

    @PutMapping("/{movie_id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UpdateMovieResponse> updateMovie(
            @PathVariable("movie_id") Long movieId,
            @Valid @RequestBody UpdateMovieRequest updateMovieRequest,
            BindingResult bindingResult) throws MovieNotFoundException {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT);
        }
        Movie movie = movieService.updateMovie(movieId, updateMovieRequest);
        return ResponseEntity.ok(new UpdateMovieResponse(movie));
    }

    @DeleteMapping("/{movie_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteMovie(@PathVariable("movie_id") Long movieId) {
        movieService.deleteMovie(movieId);
        return ResponseEntity.ok().build();
    }
}


