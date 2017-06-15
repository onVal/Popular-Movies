package com.onval.popular_movies;

import android.content.Context;

import com.onval.popular_movies.Utilities.Utilities;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by gval on 14/06/2017.
 */

public class GridPresenter implements PresenterInterface {
    private GridInterface gridInterface;
    private MovieFetcher fetcher;

    GridPresenter(GridInterface view) {
        gridInterface = view;
        this.fetcher = new MovieFetcher(view, this);
    }

    void fetchMoviesAsync() {
        fetcher.fetchNextPage();
    }

    @Override
    public void processJSONResponse(JSONObject response) {
        GridFragment view = (GridFragment) gridInterface;
        Context context = view.getContext();

        fetcher.addMoviesFromJSON(response, view.mMovieDetailsArray);

        String sortOption = Utilities.getSortOption(context);
        sortMovies(context, view.mMovieDetailsArray, sortOption);
    }

    public void sortMovies(Context context, ArrayList<MovieDetail> movieDetails, String sortOption) {

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
