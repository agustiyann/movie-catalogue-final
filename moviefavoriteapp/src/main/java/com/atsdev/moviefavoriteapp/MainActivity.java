package com.atsdev.moviefavoriteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.atsdev.moviefavoriteapp.adapter.MovieAdapter;
import com.atsdev.moviefavoriteapp.content.MovieViewModel;
import com.atsdev.moviefavoriteapp.entity.MovieItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvMovie;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMovie = findViewById(R.id.rv_fav_movie);
        rvMovie.setHasFixedSize(true);

        showRecyclerView();
    }

    private void showRecyclerView() {
        rvMovie.setLayoutManager(new LinearLayoutManager(this));
        MovieViewModel viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        viewModel.getContext(this);
        viewModel.setListMovie();
        viewModel.getMovie().observe(this, getMovies);
        movieAdapter = new MovieAdapter();
        movieAdapter.notifyDataSetChanged();
        rvMovie.setAdapter(movieAdapter);
    }

    private final Observer<ArrayList<MovieItem>> getMovies = new Observer<ArrayList<MovieItem>>() {
        @Override
        public void onChanged(ArrayList<MovieItem> movies) {
            if(movies != null){
                movieAdapter.setMovieData(movies);
            }
        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.language_settings){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
