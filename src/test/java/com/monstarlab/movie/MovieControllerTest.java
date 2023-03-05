package com.monstarlab.movie;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monstarlab.movie.controllers.MovieController;
import com.monstarlab.movie.models.Movie;
import com.monstarlab.movie.models.enums.MovieType;
import com.monstarlab.movie.payload.request.CreateMovieRequest;
import com.monstarlab.movie.payload.request.UpdateMovieRequest;
import com.monstarlab.movie.payload.response.movie.CreateMovieResponse;
import com.monstarlab.movie.payload.response.movie.GetMovieResponse;
import com.monstarlab.movie.payload.response.movie.ListMovieResponse;
import com.monstarlab.movie.payload.response.movie.UpdateMovieResponse;
import com.monstarlab.movie.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MovieControllerTest {
    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    @Mock
    private BindingResult mockBindingResult;

    private MockMvc mockMvc;

    @BeforeEach
    public void setupTest() {
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
        MockitoAnnotations.initMocks(this);
        Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
    }

    @Test
    public void testGetMovies() throws Exception {
        int page = 0;
        int size = 10;
        String searchString = "The Matrix";
        String sort = "asc";
        Movie movie = new Movie();
        movie.setTitle("Titanic");
        List<Movie> movies = Arrays.asList(movie);
        Page<Movie> moviePage = new PageImpl<>(movies);
        ListMovieResponse expectedResponse = new ListMovieResponse(false, movies);
        when(movieService.listMovies(MovieService.getPageable(page, size, sort))).thenReturn(moviePage);

        // when
        MvcResult mvcResult = mockMvc.perform(get("/api/movies"))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String responseJson = mvcResult.getResponse().getContentAsString();
        ListMovieResponse actualResponse = new ObjectMapper().readValue(responseJson, ListMovieResponse.class);
        assertEquals("Check movie title",
                expectedResponse.getMovieList().get(0).getTitle(),
                actualResponse.getMovieList().get(0).getTitle());
    }

    @Test
    public void testSearchMovies() throws Exception {
        int page = 0;
        int size = 10;
        String searchString = "title=The Matrix";
        String sort = "asc";
        Movie movie = new Movie();
        movie.setTitle("Titanic");
        List<Movie> movies = Arrays.asList(movie);
        Page<Movie> moviePage = new PageImpl<>(movies);
        ListMovieResponse expectedResponse = new ListMovieResponse(false, movies);
        when(movieService.searchMovie(searchString, MovieService.getPageable(page, size, sort))).thenReturn(moviePage);

        // when
        MvcResult mvcResult = mockMvc.perform(get("/api/movies").param("search", searchString))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String responseJson = mvcResult.getResponse().getContentAsString();
        ListMovieResponse actualResponse = new ObjectMapper().readValue(responseJson, ListMovieResponse.class);
        assertEquals("Check movie title",
                expectedResponse.getMovieList().get(0).getTitle(),
                actualResponse.getMovieList().get(0).getTitle());
    }

    @Test
    public void testGetMovie() throws Exception {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Titanic");
        GetMovieResponse expectedResponse = new GetMovieResponse(movie);
        when(movieService.getMovie(anyLong())).thenReturn(movie);

        // when
        MvcResult mvcResult = mockMvc.perform(get("/api/movies/1"))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String responseJson = mvcResult.getResponse().getContentAsString();
        GetMovieResponse actualResponse = new ObjectMapper().readValue(responseJson, GetMovieResponse.class);
        assertEquals("Check movie title",
                expectedResponse.getMovie().getTitle(),
                actualResponse.getMovie().getTitle());
    }

    @Test
    public void testCreateMovie() throws Exception {
        // given
        CreateMovieRequest request = new CreateMovieRequest();
        request.setTitle("Titanic");
        request.setDescription("");
        request.setReleaseDate("1997-10-03");
        request.setType(MovieType.ROMANTIC.name());

        Movie movie = new Movie();
        movie.setTitle("Titanic");
        movie.setDescription("");
        movie.setReleaseDate(Timestamp.valueOf("1997-10-03" + " 00:00:00"));
        movie.setType(MovieType.ROMANTIC);
        CreateMovieResponse expectedResponse = new CreateMovieResponse(movie);

        when(movieService.createMovie(any())).thenReturn(movie);

        // when
        MvcResult mvcResult = mockMvc.perform(post("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();
        // then
        String responseJson = mvcResult.getResponse().getContentAsString();
        CreateMovieResponse actualResponse = new ObjectMapper().readValue(responseJson, CreateMovieResponse.class);
        assertEquals("Check movie title",
                expectedResponse.getMovie().getTitle(),
                actualResponse.getMovie().getTitle());
        assertEquals("Check movie description",
                expectedResponse.getMovie().getDescription(),
                actualResponse.getMovie().getDescription());
        assertEquals("Check movie release date",
                expectedResponse.getMovie().getReleaseDate(),
                actualResponse.getMovie().getReleaseDate());
        assertEquals("Check movie type",
                expectedResponse.getMovie().getType(),
                actualResponse.getMovie().getType());
    }

    @Test
    public void testUpdateMovie() throws Exception {
        Long movieId = 1L;
        UpdateMovieRequest request = new UpdateMovieRequest();
        request.setTitle("Titanic");
        request.setDescription("");
        request.setReleaseDate("1997-10-03");
        request.setType(MovieType.ROMANTIC.name());

        Movie updatedMovie = new Movie();
        updatedMovie.setId(movieId);
        updatedMovie.setTitle(request.getTitle());
        updatedMovie.setReleaseDate(Timestamp.valueOf("1997-10-03" + " 00:00:00"));
        updatedMovie.setType(MovieType.ROMANTIC);
        when(movieService.updateMovie(eq(1L), any())).thenReturn(updatedMovie);

        // when
        MvcResult mvcResult = mockMvc.perform(put("/api/movies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        UpdateMovieResponse actualResponse = new ObjectMapper().readValue(responseJson, UpdateMovieResponse.class);
        assertEquals("Check movie title",
                updatedMovie.getTitle(),
                actualResponse.getMovie().getTitle());
        assertEquals("Check movie description",
                updatedMovie.getDescription(),
                actualResponse.getMovie().getDescription());
        assertEquals("Check movie release date",
                updatedMovie.getReleaseDate(),
                actualResponse.getMovie().getReleaseDate());
        assertEquals("Check movie type",
                updatedMovie.getType(),
                actualResponse.getMovie().getType());
    }

    @Test
    public void testDeleteMovie() throws Exception {
        Long movieId = 1L;
        doNothing().when(movieService).deleteMovie(movieId);

        mockMvc.perform(delete("/api/movies/1"))
                .andExpect(status().isOk());

        verify(movieService, times(1)).deleteMovie(movieId);
    }
}
