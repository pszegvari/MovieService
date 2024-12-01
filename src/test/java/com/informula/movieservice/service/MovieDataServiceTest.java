package com.informula.movieservice.service;

import com.informula.movieservice.model.MovieApi;
import com.informula.movieservice.model.dto.MovieDataResponseList;
import com.informula.movieservice.repository.MovieDataRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MovieDataServiceTest {

    @Mock
    private MovieDataRequestRepository repository;

    @Mock
    private MovieApiConsumer movieApiConsumer;

    @InjectMocks
    private MovieDataService movieDataService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Fetch movie data from API and save request - Valid input")
    void testGetMovieData_ValidInput() {
        var title = "Braveheart";
        var api = MovieApi.OMDB;

        var mockResponse = new MovieDataResponseList();
        when(movieApiConsumer.fetchMovieDataFromApi(title, api)).thenReturn(mockResponse);

        var result = movieDataService.getMovieData(title, api);

        assertThat(result).isSameAs(mockResponse);

        verify(repository, times(1)).save(argThat(entity ->
                entity.getMovieApi() == api &&
                        entity.getMovieTitle().equals(title) &&
                        entity.getRequestTime() != null
        ));
        verify(movieApiConsumer, times(1)).fetchMovieDataFromApi(title, api);
    }

    @Test
    @DisplayName("Save request is called with correct parameters")
    void testSaveRequest_ValidInput() {
        var title = "The Matrix";
        var api = MovieApi.TMDB;

        movieDataService.getMovieData(title, api);

        verify(repository, times(1)).save(argThat(entity ->
                entity.getMovieApi() == api &&
                        entity.getMovieTitle().equals(title) &&
                        entity.getRequestTime() != null
        ));
    }

    @Test
    @DisplayName("Fetch movie data from API - Valid input only")
    void testFetchMovieDataFromApi_ValidInput() {
        var title = "The Matrix";
        var api = MovieApi.TMDB;

        MovieDataResponseList mockResponse = new MovieDataResponseList();
        when(movieApiConsumer.fetchMovieDataFromApi(title, api)).thenReturn(mockResponse);

        MovieDataResponseList result = movieDataService.getMovieData(title, api);

        assertThat(result).isSameAs(mockResponse);
        verify(movieApiConsumer, times(1)).fetchMovieDataFromApi(title, api);
    }
}