package com.example.moviecatalogue.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResult {
    @SerializedName("results")
    private List<Film> resultsMovie;

    public MovieResult(List<Film> resultsMovie) {
        this.resultsMovie = resultsMovie;
    }

    public List<Film> getResultsMovie() {
        return resultsMovie;
    }

    public void setResultsMovie(List<Film> resultsMovie) {
        this.resultsMovie = resultsMovie;
    }
}
