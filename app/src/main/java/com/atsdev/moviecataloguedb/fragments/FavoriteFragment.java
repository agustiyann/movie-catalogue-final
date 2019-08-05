package com.atsdev.moviecataloguedb.fragments;


import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atsdev.moviecataloguedb.R;
import com.atsdev.moviecataloguedb.fragments.favorite.MovieFavoriteFragment;
import com.atsdev.moviecataloguedb.fragments.favorite.TvShowFavoriteFragment;

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

        String movie = getResources().getString(R.string.tab_movie);
        String tv_show = getResources().getString(R.string.tab_tvshow);

        TabLayout tabLayout = view.findViewById(R.id.tablayout_id);
        tabLayout.addTab(tabLayout.newTab().setText(movie));
        tabLayout.addTab(tabLayout.newTab().setText(tv_show));

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
            super(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.mNumOfTabs = NumOfTabs;
        }


        @NotNull
        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new MovieFavoriteFragment();

                case 1:
                    return new TvShowFavoriteFragment();

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
