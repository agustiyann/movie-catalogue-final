package com.atsdev.moviecataloguedb.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY = "com.atsdev.moviecataloguedb";
    private static final String SCHEME = "content";

    public static final String AUTHORITY_TV = "com.atsdev.moviecataloguedb.tvshow";

    public static final class MovieColumns implements BaseColumns {

        public static final String TABLE_NAME = "favorite";

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

    public static final class TvShowColumns implements BaseColumns {

        public static final String TABLE_NAME_TV = "favoritetv";

        public static final String KEY_ID_TV = "_id";
        public static final String POSTER_TV = "poster";
        public static final String TITLE_TV = "title";
        public static final String RELEASE_DATE_TV = "date";
        public static final String VOTE_TV = "voteaverage";
        public static final String POPULARITY_TV = "popularity";
        public static final String ORIGINAL_LANGUAGE_TV = "language";
        public static final String ORIGINAL_TITLE_TV = "originaltitle";
        public static final String OVERVIEW_TV = "overview";
        public static final String BLUR_IMAGE_TV = "blur";

        public static final Uri CONTENT_URI_TV = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY_TV)
                .appendPath(TABLE_NAME_TV)
                .build();
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }
}
