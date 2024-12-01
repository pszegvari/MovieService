package com.informula.movieservice.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogConstants {

    public static final String LOG_METHOD_CALLED = "Method '{}' called.";
    public static final String LOG_METHOD_DONE = "Method '{}' done.";

    public static final String METHOD_GET_MOVIE_DATA = "getMovieData";

    public static final String API_NAME = "apiName";
    public static final String TITLE = "title";

}
