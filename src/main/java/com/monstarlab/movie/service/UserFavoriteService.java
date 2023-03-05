package com.monstarlab.movie.service;

import com.monstarlab.movie.Exceptions.MovieNotFoundException;
import com.monstarlab.movie.Exceptions.UserNotFoundException;
import com.monstarlab.movie.models.Favorite;
import com.monstarlab.movie.models.Movie;
import com.monstarlab.movie.models.User;
import com.monstarlab.movie.payload.request.CreateFavoriteRequest;
import com.monstarlab.movie.repository.FavoriteRepository;
import com.monstarlab.movie.repository.MovieRepository;
import com.monstarlab.movie.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

import static com.monstarlab.movie.Constants.Constant.MOVIE_NOT_FOUND;
import static com.monstarlab.movie.Constants.Constant.USER_NOT_FOUND;

@Service
public class UserFavoriteService {

    private static final Logger log = LoggerFactory.getLogger(UserFavoriteService.class);

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public Favorite hasAlreadyAddedAsFavorite(Long userId, Long movieId) {
        String jpql = "SELECT f FROM Favorite f WHERE f.userId = :userId AND f.movieId = :movieId";
        List result = entityManager.createQuery(jpql)
                .setParameter("userId", userId)
                .setParameter("movieId", movieId)
                .getResultList();
        return result.size() == 0 ? null : (Favorite) result.get(0);
    }

    public Page<Favorite> listFavorites(Long userId, Pageable pageable) {
        Page<Favorite> favoritePage = favoriteRepository.findByUserId(userId, pageable);
        List<Favorite> favorites = favoritePage.getContent();
        if (favoritePage.hasNext()) {
            favorites.get(favorites.size() - 1).setHasNextPage(true);
        }
        return new PageImpl<>(favorites, pageable, favoritePage.getTotalElements());
    }

    @Transactional
    public Favorite createFavorite(Long userId, CreateFavoriteRequest createFavoriteRequest) throws MovieNotFoundException {
        Favorite added = hasAlreadyAddedAsFavorite(userId, createFavoriteRequest.getMovieId());
        if (added != null) {
            return added;
        }

        Movie movie = movieRepository.findById(createFavoriteRequest.getMovieId())
                .orElseThrow(() -> new MovieNotFoundException(MOVIE_NOT_FOUND));
        try {
            Favorite favorite = new Favorite();
            favorite.setUserId(userId);
            favorite.setMovieId(movie.getId());
            favorite.setMovieTitle(movie.getTitle());
            favorite = favoriteRepository.save(favorite);
            return favorite;
        } catch (DataAccessException e) {
            // Log the error
            log.error("save favorite failed, ", e);
            return null;
        }
    }

    @Transactional
    public void deleteFavorite(Long userId, Long movieId) throws MovieNotFoundException, UserNotFoundException {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(MOVIE_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        favoriteRepository.deleteByUserIdAndMovieId(userId, movie.getId());
    }
}
