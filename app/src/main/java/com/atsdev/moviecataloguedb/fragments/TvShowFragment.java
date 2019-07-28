package com.atsdev.moviecataloguedb.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import com.atsdev.moviecataloguedb.adapter.TvShowAdapter;
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
//                ItemClickSupport.addTo(rvTvShow).setOnItemClickListener((recyclerView, position, v) ->
//                        showSelectedData(movieData.get(position)));
            }
        }
    };

//    private void showSelectedData(TvShowItem movieData) {
//        Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
//        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieData);
//        startActivity(intent);
//    }

    private void showRecycleCardView(View view) {
        tvShowAdapter = new TvShowAdapter();
        tvShowAdapter.notifyDataSetChanged();
        rvTvShow.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, view.isInLayout()));
        rvTvShow.setAdapter(tvShowAdapter);
    }
}
