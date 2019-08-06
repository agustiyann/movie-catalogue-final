package com.atsdev.moviefavoriteapp.content;

import android.annotation.SuppressLint;
import android.content.ContentProviderClient;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.atsdev.moviefavoriteapp.entity.MovieItem;

import java.util.ArrayList;

import static com.atsdev.moviefavoriteapp.database.DatabaseContract.MovieColumns.CONTENT_URI;

public class MovieViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<MovieItem>> listMovie = new MutableLiveData<>();
    private Context mContext;

    public void setListMovie() {
        Uri uri = Uri.parse("content://com.atsdev.moviecataloguedb/favorite");
        @SuppressLint("Recycle")
        ContentProviderClient client = mContext.getContentResolver().acquireContentProviderClient(uri);

        try {
            ArrayList<MovieItem> arrayList = new ArrayList<>();
            assert client != null;
            Cursor cursor = client.query(CONTENT_URI, null, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            if (cursor.getCount() > 0) {
                do {
                    MovieItem item = new MovieItem();
                    item.setId(Integer.valueOf(cursor.getString(0)));
                    item.setTitle(cursor.getString(1));
                    item.setOverview(cursor.getString(2));
                    item.setPoster(cursor.getString(3));
                    item.setPopularity(cursor.getString(4));
                    item.setOriginalLanguage(cursor.getString(5));
                    item.setRelease(cursor.getString(6));
                    item.setVoteAverage(cursor.getString(7));
                    item.setOriginalTitle(cursor.getString(8));
                    arrayList.add(item);
                    cursor.moveToNext();
                } while (!cursor.isAfterLast());
            }
            cursor.close();
            listMovie.postValue(arrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LiveData<ArrayList<MovieItem>> getMovie() {
        return listMovie;
    }
    public void getContext(Context context){
        this.mContext = context;
    }
}
