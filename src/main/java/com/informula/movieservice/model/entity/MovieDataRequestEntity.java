package com.informula.movieservice.model.entity;

import com.informula.movieservice.model.MovieApi;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MOVIE_DATA_REQUEST")
@Entity
public class MovieDataRequestEntity extends BaseEntity {

    @Column(name = "MOVIE_API")
    @Enumerated(value = EnumType.STRING)
    private MovieApi movieApi;

    @Column(name = "MOVIE_TITLE")
    private String movieTitle;

    @Column(name = "REQUEST_TIME")
    private ZonedDateTime requestTime;

}
