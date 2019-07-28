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

//    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
//                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
//                    " %s TEXT NOT NULL," +
//                    " %s TEXT NOT NULL," +
//                    " %s TEXT NOT NULL," +
//                    " %s TEXT NOT NULL," +
//                    " %s TEXT NOT NULL," +
//                    " %s TEXT NOT NULL," +
//                    " $s TEXT NOT NULL," +
//                    " %s TEXT NOT NULL," +
//                    " %S TEXT NOT NULL," +
//                    " %s TEXT NOT NULL)",
//            TABLE_NAME,
//            KEY_ID,
//            TITLE,
//            RELEASE_DATE,
//            POSTER,
//            BLUR_IMAGE,
//            ORIGINAL_TITLE,
//            ORIGINAL_LANGUAGE,
//            POPULARITY,
//            VOTE,
//            OVERVIEW
//    );

//    private static final String SQL_CREATE_TABLE_TV = String.format("CREATE TABLE %s"
//                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
//                    " %s TEXT NOT NULL," +
//                    " %s TEXT NOT NULL," +
//                    " %s TEXT NOT NULL," +
//                    " %s TEXT NOT NULL," +
//                    " %s TEXT NOT NULL," +
//                    " %s TEXT NOT NULL," +
//                    " %s TEXT NOT NULL)",
//            TABLE_TV,
//            KEY_ID,
//            TITLE_TV,
//            RELEASE_DATE_TV,
//            POSTER_TV,
//            BLUR_IMAGE_TV,
//            ORIGINAL_TITLE_TV,
//            ORIGINAL_LANGUAGE_TV,
//            DESCRIPTION_TV
//    );

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

//        String CREATE_TABLE_FAVORITE_MOVIE="create table " + TABLE_NAME_TV_SERIES + " ( " +
//                COLUMN_ID + " integer primary key autoincrement, " +
//                COLUMN_NAME2 + " text not null, " +
//                COLUMN_DESC2 + " text not null, " +
//                COLUMN_POSTER2 + " text not null, " +
//                COLUMN_POPULARITY2 + " text not null, " +
//                COLUMN_LANGUAGE2 + " text not null, " +
//                COLUMN_SERIES_RELEASE2 + " text not null, " +
//                COLUMN_VOTE2 + " text not null, " +
//                COLUMN_BACKDROP_POSTER2 + " text not null " +
//                " ); " ;
//        db.execSQL(CREATE_TABLE_FAVORITE_MOVIE
//        );
    }

    public DatabaseMovieHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(CREATE_TABLE_MOVIE);
////        db.execSQL(SQL_CREATE_TABLE_TV);
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_TV);
        onCreate(db);
    }
}
