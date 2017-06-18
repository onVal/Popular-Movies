package com.onval.popular_movies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gval on 16/06/2017.
 */

public class MovieDatabaseHelper extends SQLiteOpenHelper {

    public MovieDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE = "CREATE TABLE " + MovieContract.Favorites.TABLE_NAME + " ( " +
                MovieContract.Favorites._ID + " integer PRIMARY KEY" +
                MovieContract.Favorites.MOVIE_ID + "integer UNIQUE NOT NULL );";

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // do nothing (?)
    }
}
