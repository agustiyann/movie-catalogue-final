package com.atsdev.moviecataloguedb.fragments.favorite;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.atsdev.moviecataloguedb.R;
import com.atsdev.moviecataloguedb.adapter.TvShowFavoriteAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.CONTENT_URI_TV;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFavoriteFragment extends Fragment {

    private ProgressBar progressBar;
    private TvShowFavoriteAdapter tvShowFavoriteAdapter;
    private Cursor cursor;

    public TvShowFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBarTvShow);

        tvShowFavoriteAdapter = new TvShowFavoriteAdapter(getContext());
        tvShowFavoriteAdapter.setCursor(cursor);
        tvShowFavoriteAdapter.notifyDataSetChanged();

        RecyclerView rvTvFavorite = view.findViewById(R.id.rv_fav_tv);
        rvTvFavorite.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTvFavorite.setAdapter(tvShowFavoriteAdapter);
        rvTvFavorite.setHasFixedSize(true);

        new LoadNoteAsync().execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadNoteAsync().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadNoteAsync extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected Cursor doInBackground(Void... voids) {
            return Objects.requireNonNull(getContext()).getContentResolver().query(CONTENT_URI_TV, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor favorite) {
            super.onPostExecute(favorite);
            progressBar.setVisibility(View.GONE);

            cursor = favorite;

            tvShowFavoriteAdapter.setCursor(cursor);
            tvShowFavoriteAdapter.notifyDataSetChanged();

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
