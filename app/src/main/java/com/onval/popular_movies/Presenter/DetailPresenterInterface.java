package com.onval.popular_movies.Presenter;

import android.content.Context;

import com.onval.popular_movies.MovieDetail;

/**
 * Created by gval on 18/06/2017.
 */

public interface DetailPresenterInterface {
    void favoriteClicked(Context context, MovieDetail movie);
    void loadTrailers(Context context, MovieDetail movie);
    void loadReviews(Context context, MovieDetail movie);
}
