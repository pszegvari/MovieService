package com.informula.movieservice.controller.api;

import com.informula.movieservice.model.MovieApi;
import com.informula.movieservice.model.dto.MovieDataResponseList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * A RESTful HTTP API designed to provide movie data by 3rd party providers
 */
public interface MovieDataControllerApi {

    @Operation(
            operationId = "getMovieData",
            summary = "Collects data about movies available through the API by filtering for title",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieDataResponseList.class))),
                    @ApiResponse(responseCode = "404", description = "No movies found", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieDataResponseList.class))),
            }
    )
    @GetMapping(
            value = "/movies/{movieTitle}",
            produces = {"application/json"}
    )
    ResponseEntity<MovieDataResponseList> getMovieData(@Valid @PathVariable @NotNull String movieTitle, @Valid @RequestParam(name = "api") MovieApi apiName);


}
