package com.atsdev.moviecataloguedb.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.BLUR_IMAGE;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.ORIGINAL_LANGUAGE;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.ORIGINAL_TITLE;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.OVERVIEW;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.POPULARITY;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.POSTER;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.TABLE_NAME;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.TITLE;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.VOTE;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.KEY_ID;

class DatabaseMovieHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db.moviefavorite";

    private static final int DATABASE_VERSION = 1;

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_MOVIE="create table " + TABLE_NAME + " ( " +
                KEY_ID + " integer primary key autoincrement, " +
                TITLE + " text not null, " +
                OVERVIEW + " text not null, " +
                POSTER + " text not null, " +
                POPULARITY + " text not null, " +
                ORIGINAL_LANGUAGE + " text not null, " +
                RELEASE_DATE + " text not null, " +
                VOTE + " text not null, " +
                BLUR_IMAGE + " text not null, " +
                ORIGINAL_TITLE + " text not null " +
                " ); " ;
        db.execSQL(CREATE_TABLE_MOVIE
        );
    }

    public DatabaseMovieHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }
}
