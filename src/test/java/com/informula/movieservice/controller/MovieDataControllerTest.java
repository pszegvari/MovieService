package com.informula.movieservice.controller;

import com.informula.movieservice.model.MovieApi;
import com.informula.movieservice.model.dto.MovieDataResponseList;
import com.informula.movieservice.service.MovieDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MovieDataControllerTest {

    @Mock
    private MovieDataService movieDataService;

    @InjectMocks
    private MovieDataController movieDataController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test getMovieData returns 200 OK when data is found")
    void testGetMovieData_Success() {
        var movieTitle = "Braveheart";
        var apiName = MovieApi.TMDB;

        var movieDataResponseList = new MovieDataResponseList();
        movieDataResponseList.setCount(1);
        when(movieDataService.getMovieData(movieTitle, apiName)).thenReturn(movieDataResponseList);

        ResponseEntity<MovieDataResponseList> response = movieDataController.getMovieData(movieTitle, apiName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movieDataResponseList, response.getBody());
        verify(movieDataService, times(1)).getMovieData(movieTitle, apiName);
    }

    @Test
    @DisplayName("Test getMovieData returns 404 NOT FOUND when no data is found")
    void testGetMovieData_NotFound() {
        var movieTitle = "Title of unknown movie";
        var apiName = MovieApi.TMDB;

        var movieDataResponseList = new MovieDataResponseList();
        movieDataResponseList.setCount(0);
        when(movieDataService.getMovieData(movieTitle, apiName)).thenReturn(movieDataResponseList);

        ResponseEntity<MovieDataResponseList> response = movieDataController.getMovieData(movieTitle, apiName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(movieDataResponseList, response.getBody());
        verify(movieDataService, times(1)).getMovieData(movieTitle, apiName);
    }

    @Test
    @DisplayName("Test getMovieData returns 500 INTERNAL SERVER ERROR on exception")
    void testGetMovieData_Exception() {
        var movieTitle = "Braveheart";
        var apiName = MovieApi.TMDB;

        when(movieDataService.getMovieData(movieTitle, apiName)).thenThrow(new RuntimeException("Error occured in external service"));

        ResponseEntity<MovieDataResponseList> response = movieDataController.getMovieData(movieTitle, apiName);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(movieDataService, times(1)).getMovieData(movieTitle, apiName);
    }
}