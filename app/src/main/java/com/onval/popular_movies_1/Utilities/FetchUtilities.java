package com.onval.popular_movies_1.Utilities;

import android.content.Context;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.onval.popular_movies_1.BuildConfig;
import com.onval.popular_movies_1.MovieDetail;
import com.onval.popular_movies_1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;

import static com.onval.popular_movies_1.GridFragment.movieDetails;

/**
 * Created by gval on 29/05/2017.
 */

public class FetchUtilities {
    final static private String LOG_TAG = FetchUtilities.class.getSimpleName();

    final static private String BASE_URL ="https://api.themoviedb.org/3/discover/movie";
    final static private String API_KEY_PARAM = "api_key";
    final static private String PAGE_NUM_PARAM = "page";
    final static private int DEFAULT_NUMBER_OF_PAGES = 2;

    private static Uri createMoviesUri(int pageNumber) {

        return Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIEDB_API_KEY)
                .appendQueryParameter(PAGE_NUM_PARAM, String.valueOf(pageNumber))
                .build();
    }

    // Use volley library to fetch movie data in JSON format
    public static void fetchMoviesToArray(final Context context) {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);

        for (int i = 1; i <= DEFAULT_NUMBER_OF_PAGES; i++) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    createMoviesUri(DEFAULT_NUMBER_OF_PAGES).toString(),              // string url
                    null,                                       // optional JSONObject parameter
                    new Response.Listener<JSONObject>() {       // when finishes
                        @Override
                        public void onResponse(JSONObject response) {
                            createMovieListFromJSON(response);
                            debug(); //tmp
                            sortMovies(context);
                        }
                    },
                    new Response.ErrorListener() {              // when error
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //TODO: To implement
                            Log.e(LOG_TAG, "onErrorResponse: an error has occurred.");
                        }
                    }
            );
            //Add the request queues sequentially
            requestQueue.add(jsonObjectRequest);
        }
    }

    //DEBUG METHOD
    public static void debug() {
        for (MovieDetail m  : movieDetails) {
            Log.d(LOG_TAG, m.getTitle());
            Log.d(LOG_TAG, m.getRelease_date());
            Log.d(LOG_TAG, m.getPosterPath());
        }
    }

    // creates the arrayList of movie details from the json data
    // returns null if something goes wrong
    private static void createMovieListFromJSON(JSONObject jsonObject) {
        String title;
        String posterPath; //url path
        String overview;
        double vote_average;
        String release_date;

        try {
            JSONArray jsonResultsArray =  jsonObject.getJSONArray("results");

            for (int i = 0; i < jsonResultsArray.length(); i++) {
                JSONObject jsonCurrentElement = jsonResultsArray.getJSONObject(i);

                title = jsonCurrentElement.getString("title");
                posterPath = jsonCurrentElement.getString("poster_path");
                overview = jsonCurrentElement.getString("overview");
                vote_average = jsonCurrentElement.getDouble("vote_average");
                release_date = jsonCurrentElement.getString("release_date");

                movieDetails.add(new MovieDetail(title, posterPath, overview, vote_average, release_date));
            }
        } catch (JSONException | NullPointerException exc) {
            exc.printStackTrace();
        }
    }

    private static void sortMovies(Context context) {
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
    }
}
