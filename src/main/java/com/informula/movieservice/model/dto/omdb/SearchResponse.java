package com.informula.movieservice.model.dto.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SearchResponse {

    @JsonProperty("Search")
    private List<SearchResult> search;

    private String totalResults;

    @JsonProperty("Response")
    private String response;
}