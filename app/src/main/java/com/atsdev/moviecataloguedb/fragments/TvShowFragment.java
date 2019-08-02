package com.atsdev.moviecataloguedb.fragments;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.atsdev.moviecataloguedb.ItemClickSupport;
import com.atsdev.moviecataloguedb.R;
import com.atsdev.moviecataloguedb.adapter.TvShowAdapter;
import com.atsdev.moviecataloguedb.details.DetailTvShowActivity;
import com.atsdev.moviecataloguedb.models.TvShowItem;
import com.atsdev.moviecataloguedb.viewmodels.TvShowViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    private TvShowAdapter tvShowAdapter;
    private RecyclerView rvTvShow;
    private ProgressBar progressBar;

    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TvShowViewModel tvShowViewModel;

        rvTvShow = view.findViewById(R.id.rv_tv);
        progressBar = view.findViewById(R.id.progressbarTv);
        progressBar.setVisibility(View.VISIBLE);

        showRecycleCardView(view);

        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.setTvShow();
        tvShowViewModel.getTvShow().observe(this, getTv);
    }

    private final Observer<ArrayList<TvShowItem>> getTv = new Observer<ArrayList<TvShowItem>>() {
        @Override
        public void onChanged(ArrayList<TvShowItem> tvShowData) {
            if (tvShowData != null) {
                tvShowAdapter.setMovieData(tvShowData);
                progressBar.setVisibility(View.GONE);
                ItemClickSupport.addTo(rvTvShow).setOnItemClickListener((recyclerView, position, v) ->
                        showSelectedData(tvShowData.get(position)));
            }
        }
    };

    private void showSelectedData(TvShowItem movieData) {
        Intent intent = new Intent(getActivity(), DetailTvShowActivity.class);
        intent.putExtra(DetailTvShowActivity.EXTRA_TVSHOW, movieData);
        startActivity(intent);
    }

    private void showRecycleCardView(View view) {
        tvShowAdapter = new TvShowAdapter();
        tvShowAdapter.notifyDataSetChanged();
        rvTvShow.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, view.isInLayout()));
        rvTvShow.setAdapter(tvShowAdapter);
    }
}
