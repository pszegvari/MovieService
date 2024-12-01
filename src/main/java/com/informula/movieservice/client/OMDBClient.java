package com.informula.movieservice.client;

import com.informula.movieservice.model.dto.omdb.MovieResponse;
import com.informula.movieservice.model.dto.omdb.SearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "omdbClient", url = "https://www.omdbapi.com")
public interface OMDBClient {

    @GetMapping("/?apikey=${spring.application.OMDB.apikey}")
    SearchResponse searchMovies(@RequestParam("s") String searchQuery,
                                @RequestParam(value = "page", required = false) Integer page);

    @GetMapping("/?apikey=${spring.application.OMDB.apikey}")
    MovieResponse getMovieById(@RequestParam("i") String imdbId);
}

