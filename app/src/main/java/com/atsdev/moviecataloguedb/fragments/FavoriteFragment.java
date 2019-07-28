package com.atsdev.moviecataloguedb.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atsdev.moviecataloguedb.R;
import com.atsdev.moviecataloguedb.fragments.favorite.MovieFavoriteFragment;
import com.atsdev.moviecataloguedb.fragments.favorite.TvShowFavoriteFagment;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {


    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tablayout_id);
        tabLayout.addTab(tabLayout.newTab().setText("Movie"));
        tabLayout.addTab(tabLayout.newTab().setText("Tv Show"));

        ViewPager viewPager = view.findViewById(R.id.viewpager_id);

        viewPager.setAdapter(new PagerAdapter
                (getFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    class PagerAdapter extends FragmentStatePagerAdapter {
        final int mNumOfTabs;

        PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }



        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new MovieFavoriteFragment();

                case 1:
                    return new TvShowFavoriteFagment();

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }

}
