package com.example.moviecatalogue.view.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.view.fragment.FavoriteMovieFragment;
import com.example.moviecatalogue.view.fragment.FavoriteTvShowFragment;
import com.example.moviecatalogue.view.fragment.MovieFragment;
import com.example.moviecatalogue.view.fragment.TvshowFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FavoriteMovieFragment();
            case 1:
                return new FavoriteTvShowFragment();
                default:
                    throw new IllegalArgumentException();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return mContext.getString(R.string.title_fav_movies);
            case 1:
                return mContext.getString(R.string.title_fav_tvshows);
                default:
                    return null;
        }
    }
}
