package com.example.moviecatalogue.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TvResult {
    @SerializedName("results")
    private List<TvShow> getTvShow;

    public TvResult(List<TvShow> getTvShow) {
        this.getTvShow = getTvShow;
    }

    public List<TvShow> getGetTvShow() {
        return getTvShow;
    }

    public void setGetTvShow(List<TvShow> getTvShow) {
        this.getTvShow = getTvShow;
    }
}
