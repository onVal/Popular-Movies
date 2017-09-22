package com.onval.popular_movies.Presenter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.onval.popular_movies.GridInterface;
import com.onval.popular_movies.Model.MovieFetcher;
import com.onval.popular_movies.MovieFragment;
import com.onval.popular_movies.Utilities.Utilities;

import org.json.JSONObject;

import static com.onval.popular_movies.Utilities.Utilities.createMovieUri;

/**
 * Created by gval on 14/06/2017.
 */

public class MoviePresenter implements PresenterInterface,
        Response.Listener<JSONObject>, Response.ErrorListener,
        RequestQueue.RequestFinishedListener<JSONObject> {

    private static final String LOG_TAG = MoviePresenter.class.getSimpleName();

//    private static final int NUM_OF_PAGES_TO_FETCH = 2;

    private GridInterface gridInterface;
    private MovieFetcher fetcher;

    public MoviePresenter(GridInterface view) {
        gridInterface = view;
        fetcher = new MovieFetcher(this, this, this);
    }

    @Override
    public void fetchMoviesAsync(Context context) {
        Uri uri = createMovieUri(context, Utilities.getSortOption(context));
        Log.d(LOG_TAG, "Uri to fetch:" + uri.toString());
        fetcher.fetch(context, uri);
    }

    @Override
    public void onResponse(JSONObject response) {
        MovieFragment view = (MovieFragment) gridInterface;

        fetcher.addMoviesFromJSON(response, view.mMovieDetailsArray);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        gridInterface.showErrorMessage(error);
    }

    @Override
    public void onRequestFinished(Request<JSONObject> request) {
        gridInterface.onMoviesFetched();
    }

//    public void sortMovies(Context context, ArrayList<MovieDetail> movieDetails, String sortOption) {
//
//        if (sortOption.equals(context.getString(R.string.pref_ratings_value))) {
//            Collections.sort(movieDetails, new Comparator<MovieDetail>() {
//                @Override
//                public int compare(MovieDetail md1, MovieDetail md2) {
//                    return ((Double) Math.signum((md2.getVoteAverage() - md1.getVoteAverage()))).intValue();
//                }
//            });
//        }
//
//        if (sortOption.equals(context.getString(R.string.pref_popularity_value))) {
//            Collections.sort(movieDetails, new Comparator<MovieDetail>() {
//                @Override
//                public int compare(MovieDetail md1, MovieDetail md2) {
//                    return ((Double) Math.signum((md2.getPopularity() - md1.getPopularity()))).intValue();
//                }
//            });
//        }
//    }
}
