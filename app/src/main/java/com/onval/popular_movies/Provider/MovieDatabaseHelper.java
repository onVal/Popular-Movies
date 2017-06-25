package com.onval.popular_movies.Provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by gval on 16/06/2017.
 */

public class MovieDatabaseHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = MovieDatabaseHelper.class.getSimpleName();

    public MovieDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE = "CREATE TABLE " + MovieContract.Movies.TABLE_NAME + " ( " +
                MovieContract.Movies._ID + " integer PRIMARY KEY, " +
                MovieContract.Movies.TITLE_COLUMN + " text NOT NULL);";

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to " +
                newVersion + ". OLD DATA WILL BE DESTROYED");
        // Drop the table
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.Movies.TABLE_NAME + ";");

        // re-create database
        onCreate(sqLiteDatabase);
    }
}
