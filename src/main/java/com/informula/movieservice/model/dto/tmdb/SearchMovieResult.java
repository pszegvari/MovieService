package com.informula.movieservice.model.dto.tmdb;

import lombok.Data;

@Data
public class SearchMovieResult {
    private int id;
    private String releaseDate;
    private String title;
}
