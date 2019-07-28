package com.atsdev.moviecataloguedb.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY = "com.atsdev.moviecataloguedb";
    public static final String SCHEME = "content";

    public static final class MovieColumns implements BaseColumns {

        public static String TABLE_NAME = "favorite";

        public static String KEY_ID = "_id";
        public static String POSTER = "poster";
        public static String TITLE = "title";
        public static String RELEASE_DATE = "date";
        public static String VOTE = "voteaverage";
        public static String POPULARITY = "popularity";
        public static String ORIGINAL_LANGUAGE = "language";
        public static String ORIGINAL_TITLE = "originaltitle";
        public static String OVERVIEW = "overview";
        public static String BLUR_IMAGE = "blur";

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
