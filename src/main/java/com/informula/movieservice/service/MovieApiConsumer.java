package com.informula.movieservice.service;

import com.informula.movieservice.client.OMDBClient;
import com.informula.movieservice.client.TMDBClient;
import com.informula.movieservice.model.MovieApi;
import com.informula.movieservice.model.dto.MovieDataResponse;
import com.informula.movieservice.model.dto.MovieDataResponseList;
import com.informula.movieservice.model.dto.omdb.MovieResponse;
import com.informula.movieservice.model.dto.omdb.SearchResponse;
import com.informula.movieservice.model.dto.omdb.SearchResult;
import com.informula.movieservice.model.dto.tmdb.Crew;
import com.informula.movieservice.model.dto.tmdb.SearchMovieResult;
import com.informula.movieservice.model.dto.tmdb.SearchMoviesResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.informula.movieservice.constants.MiscConstants.JOB_DIRECTOR;
import static com.informula.movieservice.constants.MiscConstants.UNKWNOWN_DIRECTOR_OMDB;

@Component
@RequiredArgsConstructor
public class MovieApiConsumer {

    private final OMDBClient omdbClient;
    private final TMDBClient tmdbClient;

    @Cacheable(value = "movieData", key = "{#api.name(), #title}")
    public MovieDataResponseList fetchMovieDataFromApi(String title, MovieApi api) {
        return switch (api) {
            case OMDB -> fetchOMDBResponse(title);
            case TMDB -> fetchTMDBResponse(title);
        };
    }

    private MovieDataResponseList fetchTMDBResponse(String title) {
        MovieDataResponseList response = new MovieDataResponseList();
        Optional.ofNullable(tmdbClient.searchMovies(title))
                .map(SearchMoviesResponse::getResults)
                .stream()
                .flatMap(List::stream)
                .map(SearchMovieResult::getId)
                .map(this::extractTMDBData)
                .forEach(response::addEntity);

        return response;
    }

    private MovieDataResponseList fetchOMDBResponse(String title) {
        MovieDataResponseList response = new MovieDataResponseList();
        Optional.ofNullable(omdbClient.searchMovies(title, null))
                .map(SearchResponse::getSearch)
                .stream().flatMap(List::stream)
                .map(SearchResult::getImdbID).map(omdbClient::getMovieById)
                .map(this::extractOMDBData)
                .forEach(response::addEntity);
        return response;
    }

    private MovieDataResponse extractOMDBData(MovieResponse movieResponse) {
        var movieData = new MovieDataResponse();
        movieData.setTitle(movieResponse.getTitle());
        movieData.setYear(movieResponse.getYear());

        String rawDirector = movieResponse.getDirector();
        if (rawDirector.equals(UNKWNOWN_DIRECTOR_OMDB)) {
            rawDirector = null;
        }
        movieData.setDirector(rawDirector != null ? Arrays.stream(rawDirector.split(",")).map(String::trim).toList() : List.of());
        return movieData;
    }

    private MovieDataResponse extractTMDBData(Integer movieId) {
        var movieData = new MovieDataResponse();
        fillTMDBYearAndTitle(movieId, movieData);
        fillTMDBCredits(movieId, movieData);
        return movieData;
    }

    private void fillTMDBCredits(Integer movieId, MovieDataResponse movieData) {
        var credits = tmdbClient.getMovieCredits(movieId);
        if (credits != null) {
            movieData.setDirector(
                    Optional.ofNullable(credits.getCrew())
                            .stream().flatMap(List::stream)
                            .filter(c -> JOB_DIRECTOR.equals(c.getJob())).map(Crew::getName).toList());
        }
    }

    private void fillTMDBYearAndTitle(Integer movieId, MovieDataResponse movieData) {
        var detail = tmdbClient.getMovieDetails(movieId);
        if (detail != null) {
            movieData.setYear(detail.getReleaseDate() != null ? detail.getReleaseDate().substring(0, 4) : Strings.EMPTY);
            movieData.setTitle(detail.getTitle());
        }
    }
}
