package com.onval.popular_movies.Provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.onval.popular_movies.Provider.MovieContract.Favorites;

/**
 * Created by gval on 16/06/2017.
 */

public class MovieDatabaseHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = MovieDatabaseHelper.class.getSimpleName();

    private Context context;

    public MovieDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE = "CREATE TABLE " + Favorites.TABLE_NAME + " ( " +
                Favorites._ID + " integer PRIMARY KEY, " +
                Favorites.TITLE_COLUMN + " text NOT NULL, " +
                Favorites.POSTERPATH_COLUMN + " text NOT NULL, " +
                Favorites.OVERVIEW_COLUMN + " text NOT NULL, " +
                Favorites.VOTE_AVG_COLUMN + " double NOT NULL, " +
                Favorites.POPULARITY_COLUMN + " double NOT NULL, " +
                Favorites.RELEASE_DATE_COLUMN + " text NOT NULL);";

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to " +
                newVersion + ". OLD DATA WILL BE DESTROYED");

        // Delete the database
        context.deleteDatabase(MovieContract.DATABASE_NAME);

        // re-create database
        onCreate(sqLiteDatabase);
    }
}
