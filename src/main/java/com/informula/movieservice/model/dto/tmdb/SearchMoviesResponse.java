package com.informula.movieservice.model.dto.tmdb;

import lombok.*;

import java.util.List;

@Data
public class SearchMoviesResponse {
    private int page;
    private List<SearchMovieResult> results;
    private int totalResults;
    private int totalPages;
}
