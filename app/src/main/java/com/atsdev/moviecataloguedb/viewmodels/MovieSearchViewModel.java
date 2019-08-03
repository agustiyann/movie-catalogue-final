package com.atsdev.moviecataloguedb.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.atsdev.moviecataloguedb.BuildConfig;
import com.atsdev.moviecataloguedb.models.MovieItem;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class MovieSearchViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<MovieItem>> listMovie = new MutableLiveData<>();
    private String movieName;

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setSearchMovie() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<MovieItem> listMovieItem = new ArrayList<>();
        String API_KEY = BuildConfig.TheMovieDBApi;
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US" + "&query=" + movieName + "&page=1";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray listMv = responseObject.getJSONArray("results");

                    for (int i = 0; i < listMv.length(); i++) {
                        JSONObject movie = listMv.getJSONObject(i);
                        MovieItem movieItem = new MovieItem(movie);
                        listMovieItem.add(movieItem);
                    }
                    listMovie.postValue(listMovieItem);
                } catch (Exception e) {
                    Log.d("Exception", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("Failure", Objects.requireNonNull(error.getMessage()));
            }
        });
    }

    public LiveData<ArrayList<MovieItem>> getSearchMovie() {
        return listMovie;
    }
}
