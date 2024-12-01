package com.informula.movieservice.model.dto.tmdb;

import lombok.Data;

import java.util.List;

@Data
public class CreditsResponse {
    private int id;
    private List<Crew> crew;
}