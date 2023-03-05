package com.monstarlab.movie;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monstarlab.movie.controllers.UserFavoriteController;
import com.monstarlab.movie.models.Favorite;
import com.monstarlab.movie.payload.request.CreateFavoriteRequest;
import com.monstarlab.movie.payload.response.favorite.CreateFavoriteResponse;
import com.monstarlab.movie.payload.response.favorite.ListFavoriteResponse;
import com.monstarlab.movie.service.UserFavoriteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest
public class UserFavoriteControllerTest {
    @Mock
    private UserFavoriteService userFavoriteService;

    @InjectMocks
    private UserFavoriteController userFavoriteController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userFavoriteController).build();
    }

    @Test
    public void testGetFavorites() throws Exception {
        Favorite favorite = new Favorite();
        favorite.setMovieId(1L);
        favorite.setUserId(1L);
        favorite.setMovieTitle("123");
        Page<Favorite> favoritePage = new PageImpl<>(Arrays.asList(favorite));
        ListFavoriteResponse expectedResponse = new ListFavoriteResponse(false, favoritePage.getContent());
        when(userFavoriteService.listFavorites(anyLong(), any(Pageable.class))).thenReturn(favoritePage);

        MvcResult mvcResult = mockMvc.perform(get("/api/users/1/favorites"))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        ListFavoriteResponse actualResponse = new ObjectMapper().readValue(responseJson, ListFavoriteResponse.class);
        assertEquals("Check favorite userId",
                expectedResponse.getFavoriteList().get(0).getUserId(),
                actualResponse.getFavoriteList().get(0).getUserId());

    }

    @Test
    public void testCreateFavorites() throws Exception {
        CreateFavoriteRequest request = new CreateFavoriteRequest();
        request.setMovieId(1L);
        Favorite favorite = new Favorite();
        favorite.setMovieId(1L);
        favorite.setUserId(1L);
        favorite.setMovieTitle("123");
        CreateFavoriteResponse expectedResponse = new CreateFavoriteResponse(favorite);
        when(userFavoriteService.createFavorite(anyLong(), any())).thenReturn(favorite);

        MvcResult mvcResult = mockMvc.perform(post("/api/users/1/favorites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        CreateFavoriteResponse actualResponse = new ObjectMapper().readValue(responseJson, CreateFavoriteResponse.class);
        assertEquals("Check favorite userId",
                expectedResponse.getFavorite().getUserId(),
                actualResponse.getFavorite().getUserId());
        assertEquals("Check favorite movieId",
                expectedResponse.getFavorite().getMovieId(),
                actualResponse.getFavorite().getMovieId());
    }

    @Test
    public void testDeleteFavorite() throws Exception {
        Long userId = 1L;
        Long movieId = 1L;
        doNothing().when(userFavoriteService).deleteFavorite(userId, movieId);

        mockMvc.perform(delete("/api/users/1/favorites/The Matrix"))
                .andExpect(status().isOk());

        verify(userFavoriteService, times(1)).deleteFavorite(userId, movieId);
    }

}
