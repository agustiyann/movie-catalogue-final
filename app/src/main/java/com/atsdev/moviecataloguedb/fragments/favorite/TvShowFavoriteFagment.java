package com.atsdev.moviecataloguedb.fragments.favorite;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atsdev.moviecataloguedb.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFavoriteFagment extends Fragment {


    public TvShowFavoriteFagment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show_favorite_fagment, container, false);
    }

}
