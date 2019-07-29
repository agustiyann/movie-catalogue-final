package com.atsdev.moviecataloguedb.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.atsdev.moviecataloguedb.BuildConfig;
import com.atsdev.moviecataloguedb.models.TvShowItem;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvShowViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<TvShowItem>> listTvShow = new MutableLiveData<>();

    public void setTvShow() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShowItem> tvShowItems = new ArrayList<>();
        String API_KEY = BuildConfig.TMDB_API_KEY;
        String url = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=en-US&page=1";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray listTv = responseObject.getJSONArray("results");

                    for (int i = 0; i < listTv.length(); i++) {
                        JSONObject tvShow = listTv.getJSONObject(i);
                        TvShowItem tvShowItem = new TvShowItem(tvShow);
                        tvShowItems.add(tvShowItem);
                    }
                    listTvShow.postValue(tvShowItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("Failure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<TvShowItem>> getTvShow() {
        return listTvShow;
    }
}
