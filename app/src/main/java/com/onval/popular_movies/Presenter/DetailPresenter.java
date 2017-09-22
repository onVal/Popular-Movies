package com.onval.popular_movies.Presenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.onval.popular_movies.DetailInterface;
import com.onval.popular_movies.Model.MovieFetcher;
import com.onval.popular_movies.MovieDetail;
import com.onval.popular_movies.Provider.MovieContract;
import com.onval.popular_movies.Utilities.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gval on 18/06/2017.
 */

public class DetailPresenter implements DetailPresenterInterface {
    private final static String TAG = DetailPresenter.class.getSimpleName();

    private DetailInterface view;
    private MovieFetcher fetcher;
    private TrailerResponder trailerResponder;
    private ReviewResponder reviewResponder;

    public DetailPresenter(DetailInterface view) {
        this.view = view;
        trailerResponder = new TrailerResponder();
        reviewResponder = new ReviewResponder();

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

    public void loadTrailers(Context context, MovieDetail movieDetail) {
        int id = movieDetail.getId();

        Uri uri = Utilities.createTrailerUri(id);

        fetcher = new MovieFetcher(trailerResponder, trailerResponder, null);
        fetcher.fetchFromServer(context, uri);
        //Go to TrailerResponder onResponse method to see how it continues from here
    }

    public void loadReviews(Context context, MovieDetail movieDetail) {
        int id = movieDetail.getId();

        Uri uri = Utilities.createReviewUri(id);

        fetcher = new MovieFetcher(reviewResponder, reviewResponder, null);
        fetcher.fetchFromServer(context, uri);
        //Go to ReviewResponder onResponse method to see how it continues from here
    }

    private class TrailerResponder implements
            Response.Listener<JSONObject>, Response.ErrorListener {
        @Override
        public void onResponse(JSONObject response) {
            try {
                JSONArray results = response.getJSONArray("results");
                String name, key;

                for (int i=0; ; i++) { //this loop exits when optJSONObject returns null
                    JSONObject trailer = results.optJSONObject(i);

                    if (trailer == null)
                        break; // this means that there are no more trailers: exits the loop

                    name = trailer.getString("name");
                    key = trailer.getString("key");
                    view.onLoadTrailer(name, key);
                }
            } catch (JSONException exc) {
                exc.printStackTrace();
            }

            view.restoreScrollPosition();

        }

        @Override
        public void onErrorResponse(VolleyError error) {
            // do nothing
        }
    }

    private class ReviewResponder implements Response.Listener<JSONObject>, Response.ErrorListener {
        @Override
        public void onResponse(JSONObject response) {
            try {
                JSONArray results = response.getJSONArray("results");

                String user, text;

                for (int i=0; ; i++) { //this loop exits when optJSONObject returns null
                    JSONObject review = results.optJSONObject(i);

                    if (review == null)
                        break; // this means that there are no more reviews: exits the loop

                    user = review.getString("author");
                    text = review.getString("content");

                    view.onLoadReviews(user, text);
                }
            } catch (JSONException exc) {
                exc.printStackTrace();
            }

            view.restoreScrollPosition();
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            // do nothing
        }
    }
}
