package com.onval.popular_movies.Provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by gval on 16/06/2017.
 */

public class MovieContract {
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "movies.db";

    public static final String AUTHORITY = "com.onval.popular_movies.Provider";

    private static final Uri BASE_CONTENT_URI =
            Uri.parse("content://" + AUTHORITY);

    public static final String PATH_FAVORITES = "favorites";

    public static class Favorites implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();

        public static final String TABLE_NAME = "favorites";

        public static final String TITLE_COLUMN = "movie_title";
        public static final String POSTERPATH_COLUMN = "posterpath";
        public static final String OVERVIEW_COLUMN = "overview";
        public static final String VOTE_AVG_COLUMN = "average";
        public static final String POPULARITY_COLUMN = "popularity";
        public static final String RELEASE_DATE_COLUMN = "release_date";

    }
}
