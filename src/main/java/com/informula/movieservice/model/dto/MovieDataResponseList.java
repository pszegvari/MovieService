package com.informula.movieservice.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MovieDataResponseList {

    protected List<MovieDataResponse> movies = new ArrayList<>();

    @JsonIgnore
    protected Integer count;

    public MovieDataResponseList addEntity(MovieDataResponse entity) {
        this.getMovies().add(entity);
        this.count = this.getMovies().size();
        return this;
    }
}
