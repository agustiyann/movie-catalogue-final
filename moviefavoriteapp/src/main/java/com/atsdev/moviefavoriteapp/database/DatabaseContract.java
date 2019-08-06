package com.atsdev.moviefavoriteapp.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    private static final String AUTHORITY = "com.atsdev.moviecataloguedb";
    private static final String SCHEME = "content";

    public static final class MovieColumns implements BaseColumns {

        static final String TABLE_NAME = "favorite";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
}
