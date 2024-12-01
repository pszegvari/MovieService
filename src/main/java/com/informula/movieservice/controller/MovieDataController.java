package com.informula.movieservice.controller;

import com.informula.movieservice.config.MetricProvider;
import com.informula.movieservice.controller.api.MovieDataControllerApi;
import com.informula.movieservice.model.MovieApi;
import com.informula.movieservice.model.dto.MovieDataResponseList;
import com.informula.movieservice.service.MovieDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.informula.movieservice.constants.LogConstants.*;
import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@RestController
@RequestMapping("/")
public class MovieDataController implements MovieDataControllerApi {

    private final MovieDataService movieDataService;

    public MovieDataController(MovieDataService movieDataService) {
        this.movieDataService = movieDataService;
    }

    @Override
    @MetricProvider(metricName = METHOD_GET_MOVIE_DATA)
    public ResponseEntity<MovieDataResponseList> getMovieData(String movieTitle, MovieApi apiName) {
        ResponseEntity<MovieDataResponseList> response;
        try {
            log.info("Getting data for movies. {} {}", kv(API_NAME, apiName.name()), kv(TITLE, movieTitle));

            var movies = movieDataService.getMovieData(movieTitle, apiName);
            if (Integer.valueOf(0).equals(movies.getCount())) {
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(movies);
            } else {
                response = ResponseEntity.status(HttpStatus.OK).body(movies);
            }

        } catch (Exception e) {
            log.error("Error getting data for movies. {} {}", kv(API_NAME, apiName.name()), kv(TITLE, movieTitle), e);
            response = ResponseEntity.internalServerError().build();
        }
        return response;
    }

}
