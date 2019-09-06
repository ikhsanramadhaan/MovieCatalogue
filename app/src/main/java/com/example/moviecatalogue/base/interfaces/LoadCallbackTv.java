package com.example.moviecatalogue.base.interfaces;

import com.example.moviecatalogue.model.TvShow;

import java.util.ArrayList;

public interface LoadCallbackTv {
    void preExcute();

    void postExcute(ArrayList<TvShow> tvShows);
}
