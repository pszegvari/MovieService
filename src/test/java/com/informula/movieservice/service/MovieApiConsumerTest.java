package com.informula.movieservice.service;

import com.informula.movieservice.client.OMDBClient;
import com.informula.movieservice.client.TMDBClient;
import com.informula.movieservice.model.MovieApi;
import com.informula.movieservice.model.dto.MovieDataResponseList;
import com.informula.movieservice.model.dto.omdb.MovieResponse;
import com.informula.movieservice.model.dto.omdb.SearchResponse;
import com.informula.movieservice.model.dto.omdb.SearchResult;
import com.informula.movieservice.model.dto.tmdb.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MovieApiConsumerTest {

    @Mock
    private OMDBClient omdbClient;

    @Mock
    private TMDBClient tmdbClient;

    @InjectMocks
    private MovieApiConsumer movieApiConsumer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Fetch movie data from OMDB - Valid movie title")
    void testFetchMovieDataFromApi_OMDB() {
        var title = "Inception";
        var api = MovieApi.OMDB;

        var mockSearchResponse = new SearchResponse();
        var mockResult = new SearchResult();
        mockResult.setImdbID("tt0848228");
        mockSearchResponse.setSearch(List.of(mockResult));

        var mockMovieResponse = new MovieResponse();
        mockMovieResponse.setTitle("Avengers: Age of Ultron");
        mockMovieResponse.setYear("2015");
        mockMovieResponse.setDirector("Joss Whedon");

        when(omdbClient.searchMovies(title, null)).thenReturn(mockSearchResponse);
        when(omdbClient.getMovieById("tt0848228")).thenReturn(mockMovieResponse);

        MovieDataResponseList result = movieApiConsumer.fetchMovieDataFromApi(title, api);

        assertThat(result.getMovies()).hasSize(1);
        assertThat(result.getMovies().get(0).getTitle()).isEqualTo("Avengers: Age of Ultron");
        assertThat(result.getMovies().get(0).getYear()).isEqualTo("2015");
        assertThat(result.getMovies().get(0).getDirector()).contains("Joss Whedon");

        verify(omdbClient, times(1)).searchMovies(title, null);
        verify(omdbClient, times(1)).getMovieById("tt0848228");
    }

    @Test
    @DisplayName("Fetch movie data from TMDB - Valid movie title")
    void testFetchMovieDataFromApi_TMDB() {
        var title = "Avengers: Age of Ultron";
        var api = MovieApi.TMDB;

        var mockSearchMoviesResponse = new SearchMoviesResponse();
        var mockResult = new SearchMovieResult();
        mockResult.setId(12345);
        mockSearchMoviesResponse.setResults(List.of(mockResult));

        var mockDetailResponse = new MovieDetailsResponse();
        mockDetailResponse.setTitle("Avengers: Age of Ultron");
        mockDetailResponse.setReleaseDate("2015-05-01");

        var mockCreditsResponse = new CreditsResponse();
        var director = new Crew();
        director.setJob("Director");
        director.setName("Joss Whedon");
        mockCreditsResponse.setCrew(List.of(director));

        when(tmdbClient.searchMovies(title)).thenReturn(mockSearchMoviesResponse);
        when(tmdbClient.getMovieDetails(12345)).thenReturn(mockDetailResponse);
        when(tmdbClient.getMovieCredits(12345)).thenReturn(mockCreditsResponse);

        MovieDataResponseList result = movieApiConsumer.fetchMovieDataFromApi(title, api);

        assertThat(result.getMovies()).hasSize(1);
        assertThat(result.getMovies().get(0).getTitle()).isEqualTo("Avengers: Age of Ultron");
        assertThat(result.getMovies().get(0).getYear()).isEqualTo("2015");
        assertThat(result.getMovies().get(0).getDirector()).contains("Joss Whedon");

        verify(tmdbClient, times(1)).searchMovies(title);
        verify(tmdbClient, times(1)).getMovieDetails(12345);
        verify(tmdbClient, times(1)).getMovieCredits(12345);
    }

    @Test
    @DisplayName("Fetch movie data from OMDB - Empty search response")
    void testFetchMovieDataFromApi_OMDB_EmptyResponse() {
        var title = "Unknown Movie";
        var api = MovieApi.OMDB;

        when(omdbClient.searchMovies(title, null)).thenReturn(new SearchResponse());
        MovieDataResponseList result = movieApiConsumer.fetchMovieDataFromApi(title, api);

        assertThat(result.getMovies()).isEmpty();
        verify(omdbClient, times(1)).searchMovies(title, null);
        verifyNoMoreInteractions(omdbClient);
    }

    @Test
    @DisplayName("Fetch movie data from TMDB - Empty search response")
    void testFetchMovieDataFromApi_TMDB_EmptyResponse() {
        var title = "Unknown Movie";
        var api = MovieApi.TMDB;

        when(tmdbClient.searchMovies(title)).thenReturn(new SearchMoviesResponse());

        var result = movieApiConsumer.fetchMovieDataFromApi(title, api);

        assertThat(result.getMovies()).isEmpty();
        verify(tmdbClient, times(1)).searchMovies(title);
        verifyNoMoreInteractions(tmdbClient);
    }
}