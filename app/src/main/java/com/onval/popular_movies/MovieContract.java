package com.onval.popular_movies;

import android.provider.BaseColumns;

/**
 * Created by gval on 16/06/2017.
 */

public class MovieContract {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "movies.db";

    public class Favorites implements BaseColumns {
        public static final String TABLE_NAME = "favorites";
        public static final String MOVIE_ID = "movie_id";
    }
}
