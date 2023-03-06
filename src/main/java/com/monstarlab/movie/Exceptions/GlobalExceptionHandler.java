package com.monstarlab.movie.Exceptions;

import com.monstarlab.movie.payload.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.monstarlab.movie.Constants.Constant.*;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(ex.getMessage());
        error.setErrorCode(GENERAL_EXCEPTION_ERROR_CODE);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMovieNotFoundException(MovieNotFoundException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(ex.getMessage());
        error.setErrorCode(MOVIE_NOT_FOUND_ERROR_CODE);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(ex.getMessage());
        error.setErrorCode(USER_NOT_FOUND_ERROR_CODE);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FavoriteNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFavoriteNotFoundException(FavoriteNotFoundException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(ex.getMessage());
        error.setErrorCode(FAVORITE_NOT_FOUND_ERROR_CODE);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(ex.getMessage());
        error.setErrorCode(ILLEGAL_ARGUMENT_ERROR_CODE);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PageSizeTooLargeException.class)
    public ResponseEntity<ErrorResponse> handleValidationPageSizeTooLargeException(PageSizeTooLargeException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(ex.getMessage());
        error.setErrorCode(PAGE_SIZE_TOO_LARGE_ERROR_CODE);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongSearchTermException.class)
    public ResponseEntity<ErrorResponse> handleWrongSearchTermException(WrongSearchTermException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(ex.getMessage());
        error.setErrorCode(WRONG_SEARCH_TERM_ERROR_CODE);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}