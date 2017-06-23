package com.onval.popular_movies.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.net.URISyntaxException;

/**
 * Created by gval on 16/06/2017.
 */

public class MovieProvider extends ContentProvider {
    private SQLiteDatabase database;
    private SQLiteOpenHelper helper;

    private static final int FAVORITES = 100;
    private static final int FAV_WITH_ID = 101;

    private static final UriMatcher URIMatcher = MovieProvider.buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_FAVORITES, FAVORITES);
        matcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_FAVORITES + "/#", FAV_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        helper = new MovieDatabaseHelper(getContext(), MovieContract.DATABASE_NAME, null, MovieContract.DATABASE_VERSION);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        database = helper.getReadableDatabase();
        Cursor cursor;

        try {
            switch (URIMatcher.match(uri)) {
                case FAVORITES:
                    cursor = database.query(true, MovieContract.Favorites.TABLE_NAME, null, null, null, null, null, null, null);
                    break;
                case FAV_WITH_ID:
                    String movie_id = uri.getLastPathSegment(); //extract the movie_id from uri
                    cursor = database.query(true, MovieContract.Favorites.TABLE_NAME, null,
                            MovieContract.Favorites._ID + " = " + movie_id, null, null, null, null, null);
                    break;
                default:
                    throw new URISyntaxException(uri.toString(), "Uri not valid!");
            }
        } catch (URISyntaxException exc) {
            exc.printStackTrace();
//            database.close();
            return null;
        } finally {
//            database.close();
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase database = helper.getWritableDatabase();

        try {
            switch (URIMatcher.match(uri)) {
                case FAVORITES:
                    database.insert(MovieContract.Favorites.TABLE_NAME, null, contentValues);
                    break;
                default:
                    throw new URISyntaxException(uri.toString(), "Uri not valid!");
            }
        } catch (URISyntaxException exc) {
            exc.printStackTrace();
            database.close();
            return null;
        } finally {
            database.close();
        }

        return uri.buildUpon().appendPath(contentValues.getAsString(MovieContract.Favorites._ID)).build();
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase database = helper.getWritableDatabase();
        int rowsAffected;

        try {
            switch (URIMatcher.match(uri)) {
                case FAV_WITH_ID: //delete single element
                    String movie_id = uri.getLastPathSegment();
                    rowsAffected = database.delete(MovieContract.Favorites.TABLE_NAME,
                                MovieContract.Favorites._ID + " = " + movie_id,
                                null);
                    break;
                default:
                    throw new URISyntaxException(uri.toString(), "Uri not valid!");
            }
        } catch (URISyntaxException exc) {
            exc.printStackTrace();
            database.close();
            return -1;
        } finally {
            database.close();
        }

        return rowsAffected;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
