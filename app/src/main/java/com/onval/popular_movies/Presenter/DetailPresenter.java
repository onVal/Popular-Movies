package com.onval.popular_movies.Presenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.onval.popular_movies.DetailView;
import com.onval.popular_movies.MovieDetail;
import com.onval.popular_movies.Provider.MovieContract;
import com.onval.popular_movies.Utilities.Utilities;

/**
 * Created by gval on 18/06/2017.
 */

public class DetailPresenter implements DetailPresenterInterface {
    private final static String TAG = DetailPresenter.class.getSimpleName();

    private DetailView view;
    public DetailPresenter(DetailView view) {
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
            contentValues.put(MovieContract.Favorites.MOVIE_TITLE, movie.getTitle());
            context.getContentResolver().insert(MovieContract.Favorites.CONTENT_URI, contentValues);
            view.onMarkFavorite();
        }
        cursor.close();
    }
}
