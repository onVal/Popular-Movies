package com.onval.popular_movies.Utilities;

import android.content.Context;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.onval.popular_movies.BuildConfig;
import com.onval.popular_movies.MovieDetail;
import com.onval.popular_movies.R;

/**
 * Created by gval on 29/05/2017.
 */

public class Utilities {
    final private String LOG_TAG = Utilities.class.getSimpleName();

    //Url for movies info
    final static private String BASE_URL ="https://api.themoviedb.org/3/discover/movie";
    final static private String API_KEY_PARAM = "api_key";
    final static private String PAGE_NUM_PARAM = "page";

    //Base URL to retrieve the images
    final static private String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";

    // Available options: "w92", "w154", "w185", "w342", "w500", "w780", or "original"
    final static private String IMAGE_SIZE = "w342";

    //Complete URL to retrieve the images
    final static public String IMAGE_URL = BASE_IMAGE_URL + IMAGE_SIZE;

    public static Uri createMoviesUri(int pageNumber) {

        return Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIEDB_API_KEY)
                .appendQueryParameter(PAGE_NUM_PARAM, String.valueOf(pageNumber))
                .build();
    }

    public static Uri extractImageUri(MovieDetail movie) {
        return Uri.parse(IMAGE_URL).buildUpon()
                .appendEncodedPath(movie.getPosterPath())
                .build();
    }

    public static String getSortOption(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.pref_sort_key),
                        context.getString(R.string.pref_popularity_value));
    }
}
