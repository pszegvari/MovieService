package com.informula.movieservice.model.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MovieDetailsResponse {
    private int id;
    @JsonProperty("release_date")
    private String releaseDate;
    private String title;
}
