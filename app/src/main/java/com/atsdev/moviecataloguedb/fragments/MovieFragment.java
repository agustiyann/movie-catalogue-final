package com.atsdev.moviecataloguedb.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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

import com.atsdev.moviecataloguedb.ItemClickSupport;
import com.atsdev.moviecataloguedb.R;
import com.atsdev.moviecataloguedb.adapter.MovieAdapter;
import com.atsdev.moviecataloguedb.details.DetailMovieActivity;
import com.atsdev.moviecataloguedb.models.MovieItem;
import com.atsdev.moviecataloguedb.viewmodels.MovieViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private MovieAdapter movieAdapter;
    private RecyclerView rvMovie;
    private ProgressBar progressBar;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MovieViewModel movieViewModel;

        rvMovie = view.findViewById(R.id.rv_movie);
        progressBar = view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        showRecycleCardView(view);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.setMovie();
        movieViewModel.getMovie().observe(this, getMovie);
    }

    private final Observer<ArrayList<MovieItem>> getMovie = new Observer<ArrayList<MovieItem>>() {
        @Override
        public void onChanged(ArrayList<MovieItem> movieData) {
            if (movieData != null) {
                movieAdapter.setMovieData(movieData);
                progressBar.setVisibility(View.GONE);
                ItemClickSupport.addTo(rvMovie).setOnItemClickListener((recyclerView, position, v) ->
                        showSelectedData(movieData.get(position)));
            }
        }
    };

    private void showSelectedData(MovieItem movieData) {
        Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieData);
        startActivity(intent);
    }

    private void showRecycleCardView(View view) {
        movieAdapter = new MovieAdapter();
        movieAdapter.notifyDataSetChanged();
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, view.isInLayout()));
        rvMovie.setAdapter(movieAdapter);
    }
}
