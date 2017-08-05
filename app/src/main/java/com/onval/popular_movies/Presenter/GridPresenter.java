package com.onval.popular_movies.Presenter;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.onval.popular_movies.GridFragment;
import com.onval.popular_movies.GridInterface;
import com.onval.popular_movies.Model.MovieFetcher;
import com.onval.popular_movies.MovieDetail;
import com.onval.popular_movies.R;
import com.onval.popular_movies.Utilities.Utilities;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by gval on 14/06/2017.
 */

public class GridPresenter implements PresenterInterface,
        Response.Listener<JSONObject>, Response.ErrorListener,
        RequestQueue.RequestFinishedListener<JSONObject> {

    private GridInterface gridInterface;
    private MovieFetcher fetcher;

    public GridPresenter(GridInterface view) {
        gridInterface = view;
        fetcher = new MovieFetcher(this, this, this);
    }

    public void fetchMoviesAsync() {
        Context context = ((GridFragment) gridInterface).getContext();
        fetcher.fetchNextPage(context);
    }

    @Override
    public void processJSONResponse(JSONObject response) {
        GridFragment view = (GridFragment) gridInterface;
        Context context = view.getContext();

        fetcher.addMoviesFromJSON(response, view.mMovieDetailsArray);

        String sortOption = Utilities.getSortOption(context);
        sortMovies(context, view.mMovieDetailsArray, sortOption);
    }

    @Override
    public void onResponse(JSONObject response) {
        processJSONResponse(response);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        gridInterface.showErrorMessage(error);
    }

    @Override
    public void onRequestFinished(Request<JSONObject> request) {
        gridInterface.initializeAdapter();
    }

    public void sortMovies(Context context, ArrayList<MovieDetail> movieDetails, String sortOption) {

        if (sortOption.equals(context.getString(R.string.pref_ratings_value))) {
            Collections.sort(movieDetails, new Comparator<MovieDetail>() {
                @Override
                public int compare(MovieDetail md1, MovieDetail md2) {
                    return ((Double) Math.signum((md2.getVoteAverage() - md1.getVoteAverage()))).intValue();
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
