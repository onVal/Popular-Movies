package com.onval.popular_movies.Presenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.onval.popular_movies.DetailInterface;
import com.onval.popular_movies.MovieDetail;
import com.onval.popular_movies.Provider.MovieContract;
import com.onval.popular_movies.Utilities.Utilities;

/**
 * Created by gval on 18/06/2017.
 */

public class DetailPresenter implements DetailPresenterInterface {
    private final static String TAG = DetailPresenter.class.getSimpleName();

    private DetailInterface view;
    public DetailPresenter(DetailInterface view) {
        this.view = view;
    }

    @Override
    public void favoriteClicked(Context context, MovieDetail movie) {

        Uri uriWithID = Utilities.buildUriWithId(movie.getId());

        // Checks if given movie is a favorite or not
        Cursor cursor = context.getContentResolver().query(uriWithID, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) { // remove from favorites
            context.getContentResolver().delete(uriWithID, null, null);
            view.onRemoveFavorite();
        } else { // add it as a favorite
            ContentValues contentValues = new ContentValues();
            contentValues.put(MovieContract.Favorites._ID, movie.getId());
            contentValues.put(MovieContract.Favorites.TITLE_COLUMN, movie.getTitle());
            contentValues.put(MovieContract.Favorites.POSTERPATH_COLUMN, movie.getPosterPath());
            contentValues.put(MovieContract.Favorites.OVERVIEW_COLUMN, movie.getOverview());
            contentValues.put(MovieContract.Favorites.VOTE_AVG_COLUMN, movie.getVoteAverage());
            contentValues.put(MovieContract.Favorites.RELEASE_DATE_COLUMN, movie.getRelease_date());
            contentValues.put(MovieContract.Favorites.POPULARITY_COLUMN, movie.getPopularity());

            context.getContentResolver().insert(MovieContract.Favorites.CONTENT_URI, contentValues);
            view.onMarkFavorite();
        }
        cursor.close();
    }
}
