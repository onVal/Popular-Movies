package com.onval.popular_movies.Utilities;

import android.content.Context;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.onval.popular_movies.BuildConfig;
import com.onval.popular_movies.MovieDetail;
import com.onval.popular_movies.R;
import com.onval.popular_movies.ThumbnailAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by gval on 29/05/2017.
 */

public class FetchUtilities {
    final static private String LOG_TAG = FetchUtilities.class.getSimpleName();

    //Url for movies info
    final static private String BASE_URL ="https://api.themoviedb.org/3/discover/movie";
    final static private String API_KEY_PARAM = "api_key";
    final static private String PAGE_NUM_PARAM = "page";

    //URL to retrieve the images
    final static private String BASE_IMAGE_URL = "http://image.tmdb.org/t/p";

    // Available options: "w92", "w154", "w185", "w342", "w500", "w780", or "original"
    final static private String IMAGE_SIZE = "w342";

    public static Uri createMoviesUri(int pageNumber) {

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIEDB_API_KEY)
                .appendQueryParameter(PAGE_NUM_PARAM, String.valueOf(pageNumber))
                .build();

//        Log.d(LOG_TAG, "URI: " + uri);
        return uri;
    }


    public static Uri extractImageUri(MovieDetail movie) {
        Uri uri = Uri.parse(BASE_IMAGE_URL).buildUpon()
                .appendPath(IMAGE_SIZE)
                .appendEncodedPath(movie.getPosterPath())
                .build();

//        Log.d(LOG_TAG, "Image URI: " + uri);
        return uri;
    }

    // creates the arrayList of movie details from the json data
    // returns null if something goes wrong
    public static void addMoviesFromJSON(JSONObject jsonObject, ArrayList<MovieDetail> movieDetails, ThumbnailAdapter adapter) {
        String title;
        String posterPath; //url path
        String overview;
        double vote_average;
        double popularity;
        String release_date;

        try {
            JSONArray jsonResultsArray = jsonObject.getJSONArray("results");

            for (int i = 0; i < jsonResultsArray.length(); i++) {
                JSONObject jsonCurrentElement = jsonResultsArray.getJSONObject(i);

                title = jsonCurrentElement.getString("title");
                posterPath = jsonCurrentElement.getString("poster_path");
                overview = jsonCurrentElement.getString("overview");
                vote_average = jsonCurrentElement.getDouble("vote_average");
                popularity = jsonCurrentElement.getDouble("popularity");
                release_date = jsonCurrentElement.getString("release_date");

                movieDetails.add(new MovieDetail(title, posterPath, overview, vote_average, popularity, release_date));
            }
        } catch (JSONException | NullPointerException exc) {
            exc.printStackTrace();
        }
    }

    public static void sortMovies(ArrayList<MovieDetail> movieDetails, Context context) {
        String sortOption = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.pref_sort_key),
                        context.getString(R.string.pref_popularity_value));

        if (sortOption.equals(context.getString(R.string.pref_ratings_value))) {
            Collections.sort(movieDetails, new Comparator<MovieDetail>() {
                @Override
                public int compare(MovieDetail md1, MovieDetail md2) {
                    return ((Double) Math.signum((md2.getVote_average() - md1.getVote_average()))).intValue();
                }
            });
        }

        if (sortOption.equals(context.getString(R.string.pref_popularity_value))) {
            Collections.sort(movieDetails, new Comparator<MovieDetail>() {
                @Override
                public int compare(MovieDetail md1, MovieDetail md2) {
                    return ((Double) Math.signum((md2.getPopularity() - md1.getPopularity()))).intValue();
                }
            });
        }
    }
}
