package com.onval.popular_movies.Provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by gval on 16/06/2017.
 */

public class MovieContract {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "movies.db";

    public static final String AUTHORITY = "com.onval.popular_movies.Provider";

    public static final Uri BASE_CONTENT_URI =
            Uri.parse("content://" + AUTHORITY);

    public static final String PATH_FAVORITES = "favorites";

    public static class Favorites implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();

        public static final String TABLE_NAME = "favorites";

        public static final String MOVIE_TITLE = "movie_title";
    }
}
