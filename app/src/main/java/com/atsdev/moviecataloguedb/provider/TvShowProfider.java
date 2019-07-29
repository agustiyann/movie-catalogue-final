package com.atsdev.moviecataloguedb.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.atsdev.moviecataloguedb.database.TvHelper;

import java.util.Objects;

import static com.atsdev.moviecataloguedb.database.DatabaseContract.AUTHORITY_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.CONTENT_URI_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.TABLE_NAME_TV;

public class TvShowProfider extends ContentProvider {

    private static final int TV_SHOW = 1;
    private static final int TV_SHOW_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(AUTHORITY_TV, TABLE_NAME_TV, TV_SHOW);
        sUriMatcher.addURI(AUTHORITY_TV,
                TABLE_NAME_TV + "/#",
                TV_SHOW_ID);
    }

    private TvHelper favoriteHelper;

    @Override
    public boolean onCreate() {
        favoriteHelper = new TvHelper(getContext());
        favoriteHelper.open();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case TV_SHOW:
                cursor = favoriteHelper.queryProvider();
                break;
            case TV_SHOW_ID:
                cursor = favoriteHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);
        }

        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {

        long added;

        if (sUriMatcher.match(uri) == TV_SHOW) {
            added = favoriteHelper.insertProvider(contentValues);
        } else {
            added = 0;
        }

        if (added > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI_TV + "/" + added);
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updated;
        if (sUriMatcher.match(uri) == TV_SHOW_ID) {
            updated = favoriteHelper.updateProvider(uri.getLastPathSegment(), contentValues);
        } else {
            updated = 0;
        }

        if (updated > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        int deleted;
        if (sUriMatcher.match(uri) == TV_SHOW_ID) {
            deleted = favoriteHelper.deleteProvider(uri.getLastPathSegment());
        } else {
            deleted = 0;
        }

        if (deleted > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }
}
