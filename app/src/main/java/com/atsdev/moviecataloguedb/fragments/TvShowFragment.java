package com.atsdev.moviecataloguedb.fragments;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.atsdev.moviecataloguedb.utils.ItemClickSupport;
import com.atsdev.moviecataloguedb.R;
import com.atsdev.moviecataloguedb.adapter.TvShowAdapter;
import com.atsdev.moviecataloguedb.details.DetailTvShowActivity;
import com.atsdev.moviecataloguedb.models.TvShowItem;
import com.atsdev.moviecataloguedb.viewmodels.TvShowSearchViewModel;
import com.atsdev.moviecataloguedb.viewmodels.TvShowViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    private TvShowAdapter tvShowAdapter;
    private RecyclerView rvTvShow;
    private ProgressBar progressBar;

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);

        if(searchItem != null){
            searchView = (SearchView) searchItem.getActionView();
        }
        if(searchView != null){
            searchView.setSearchableInfo(Objects.requireNonNull(searchManager).getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    progressBar.setVisibility(View.VISIBLE);
                    searchRecylerView(s);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.app_bar_search) {
            return false;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

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
                tvShowAdapter.setTvData(tvShowData);
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

    private void searchRecylerView(String s){
        rvTvShow.setLayoutManager(new LinearLayoutManager(getActivity()));

        TvShowSearchViewModel tvShowSearchViewModel = ViewModelProviders.of(this).get(TvShowSearchViewModel.class);
        tvShowSearchViewModel.getSearchTvShow().observe(this, getTvSerch);
        tvShowSearchViewModel.setTvName(s);
        tvShowSearchViewModel.setSearchTvShow();
        tvShowAdapter = new TvShowAdapter();
        tvShowAdapter.notifyDataSetChanged();

        rvTvShow.setAdapter(tvShowAdapter);
    }

    private final Observer<ArrayList<TvShowItem>> getTvSerch = new Observer<ArrayList<TvShowItem>>() {
        @Override
        public void onChanged(ArrayList<TvShowItem> tvShowItems) {
            if(tvShowItems != null){
                tvShowAdapter.setTvData(tvShowItems);
                progressBar.setVisibility(View.GONE);
                ItemClickSupport.addTo(rvTvShow).setOnItemClickListener((recyclerView, position, v) ->
                        showSelectedData(tvShowItems.get(position)));
            }
        }

    };
}
