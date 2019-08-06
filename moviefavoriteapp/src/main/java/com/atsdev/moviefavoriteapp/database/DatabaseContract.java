package com.atsdev.moviefavoriteapp.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    private static final String AUTHORITY = "com.atsdev.moviecataloguedb";
    private static final String SCHEME = "content";

    public static final class MovieColumns implements BaseColumns {

        static final String TABLE_NAME = "favorite";

        public static final String KEY_ID = "_id";
        public static final String POSTER = "poster";
        public static final String TITLE = "title";
        public static final String RELEASE_DATE = "date";
        public static final String VOTE = "voteaverage";
        public static final String POPULARITY = "popularity";
        public static final String ORIGINAL_LANGUAGE = "language";
        public static final String ORIGINAL_TITLE = "originaltitle";
        public static final String OVERVIEW = "overview";
        public static final String BLUR_IMAGE = "blur";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }
}
