package com.informula.movieservice.model.dto.tmdb;

import lombok.Data;

@Data
public class Crew {
    private int id;
    private String job; // Job title (e.g., Director, Writer)
    private String name; // Name of the crew member
}