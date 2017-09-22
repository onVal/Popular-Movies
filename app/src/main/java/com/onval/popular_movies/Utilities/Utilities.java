package com.onval.popular_movies.Utilities;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.onval.popular_movies.BuildConfig;
import com.onval.popular_movies.MovieDetail;
import com.onval.popular_movies.Provider.MovieContract;
import com.onval.popular_movies.R;

/**
 * Created by gval on 29/05/2017.
 */

public class Utilities {
    final private String LOG_TAG = Utilities.class.getSimpleName();

    //URL for movies info
    final static private String BASE_URL ="https://api.themoviedb.org/3/movie";
    final static private String API_KEY_PARAM = "api_key";
    final static private String PAGE_NUM_PARAM = "page";

    //Base URL to retrieve the images
    final static private String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";

    // Available options: "w92", "w154", "w185", "w342", "w500", "w780", or "original"
    final static private String IMAGE_SIZE = "w342";

    //Complete URL to retrieve the images
    final static public String IMAGE_URL = BASE_IMAGE_URL + IMAGE_SIZE;

    //Base URL for Youtube Trailers
    final static private String BASE_YOUTUBE_URL = "https://www.youtube.com/watch?v=";





    public static Uri createMovieUri(Context context, String sortOption) {
        if (sortOption.equals(context.getString(R.string.pref_popularity_value)))
            return createPopularUri();
        if (sortOption.equals(context.getString(R.string.pref_ratings_value)))
            return createTopRatedUri();

        return null; //shouldn't happen
    }

    //Return uri to fetch from /movie/popular
    private static Uri createPopularUri() {
        return Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath("popular")
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIEDB_API_KEY)
//                .appendQueryParameter(PAGE_NUM_PARAM, String.valueOf(pageNumber))
                .build();
    }

    //Return uri to fetch from /movie/top_rated
    private static Uri createTopRatedUri() {
        return Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath("top_rated")
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIEDB_API_KEY)
//                .appendQueryParameter(PAGE_NUM_PARAM, String.valueOf(pageNumber))
                .build();

    }

    // Return the uri for the image thumbnails
    public static Uri extractImageUri(MovieDetail movie) {
        return Uri.parse(IMAGE_URL).buildUpon()
                .appendEncodedPath(movie.getPosterPath())
                .build();
    }

    public static Uri createImageUri(String posterpath) {
        return Uri.parse(IMAGE_URL).buildUpon()
                .appendEncodedPath(posterpath)
                .build();
    }

    public static Uri createTrailerUri(int movie_id) {
        String movieIdString = String.valueOf(movie_id);

        return Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(movieIdString)
                .appendEncodedPath("videos")
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIEDB_API_KEY)
                .build();
    }

    public static Uri createReviewUri(int movie_id) {
        String movieIdString = String.valueOf(movie_id);

        return Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(movieIdString)
                .appendEncodedPath("reviews")
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIEDB_API_KEY)
                .build();
    }

    public static Uri createYoutubeUri(String key) {
        return Uri.parse(BASE_YOUTUBE_URL).buildUpon()
                .appendEncodedPath(key)
                .build();
    }

    // Get sort option as a String from the default shared preferences
    public static String getSortOption(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.pref_sort_key),
                        context.getString(R.string.pref_popularity_value));
    }

    /* This was taken from the stackoverflow answer suggested in the project detail */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static Uri buildUriWithId(int movieId) {
        String id = movieId + "";
        return MovieContract.Favorites.CONTENT_URI.buildUpon()
                .appendPath(id).build();
    }

    public static boolean isFavoritesEmpty(Context context)  {
        Cursor cursor = context.getContentResolver().query(MovieContract.Favorites.CONTENT_URI, null, null, null, null);
        boolean isEmpty = cursor.getCount() == 0;
        cursor.close();
        return isEmpty;

    }
}
