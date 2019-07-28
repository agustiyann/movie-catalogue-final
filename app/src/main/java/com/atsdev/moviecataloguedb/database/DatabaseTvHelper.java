package com.atsdev.moviecataloguedb.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.BLUR_IMAGE_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.KEY_ID_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.ORIGINAL_LANGUAGE_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.ORIGINAL_TITLE_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.OVERVIEW_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.POPULARITY_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.POSTER_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.RELEASE_DATE_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.TABLE_NAME_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.TITLE_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.VOTE_TV;

public class DatabaseTvHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db.moviefavorite";
    private static final int DATABASE_VERSION = 1;

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_TV ="create table " + TABLE_NAME_TV + " ( " +
                KEY_ID_TV + " integer primary key autoincrement, " +
                TITLE_TV + " text not null, " +
                OVERVIEW_TV + " text not null, " +
                POSTER_TV + " text not null, " +
                POPULARITY_TV + " text not null, " +
                ORIGINAL_LANGUAGE_TV + " text not null, " +
                RELEASE_DATE_TV + " text not null, " +
                VOTE_TV + " text not null, " +
                BLUR_IMAGE_TV + " text not null, " +
                ORIGINAL_TITLE_TV + " text not null " +
                " ); " ;
        db.execSQL(CREATE_TABLE_TV
        );
    }

    public DatabaseTvHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_TV);
        onCreate(db);
    }
}
