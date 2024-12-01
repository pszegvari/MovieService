package com.informula.movieservice.client;

import com.informula.movieservice.model.dto.tmdb.CreditsResponse;
import com.informula.movieservice.model.dto.tmdb.MovieDetailsResponse;
import com.informula.movieservice.model.dto.tmdb.SearchMoviesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tmdbClient", url = "https://api.themoviedb.org/3")
public interface TMDBClient {

    @GetMapping("/search/movie?include_adult=true&api_key=${spring.application.TMDB.apikey}")
    SearchMoviesResponse searchMovies(@RequestParam("query") String query);

    @GetMapping("/movie/{movie_id}?api_key=${spring.application.TMDB.apikey}")
    MovieDetailsResponse getMovieDetails(@RequestParam("movie_id") int movieId);

    @GetMapping("/movie/{movieId}/credits?api_key=${spring.application.TMDB.apikey}")
    CreditsResponse getMovieCredits(@PathVariable("movieId") int movieId);
}