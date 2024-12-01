package com.informula.movieservice.service;

import com.informula.movieservice.model.MovieApi;
import com.informula.movieservice.model.dto.MovieDataResponseList;
import com.informula.movieservice.model.entity.MovieDataRequestEntity;
import com.informula.movieservice.repository.MovieDataRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieDataService {

    private final MovieDataRequestRepository repository;
    private final MovieApiConsumer movieApiConsumer;

    public MovieDataResponseList getMovieData(String title, MovieApi api) {
        saveRequest(title, api);
        return movieApiConsumer.fetchMovieDataFromApi(title, api);
    }

    private void saveRequest(String title, MovieApi api) {
        repository.save(new MovieDataRequestEntity(api, title, ZonedDateTime.now()));
    }

}
