package com.example.moviecatalogue.base.networks;

import android.os.Build;

import com.example.moviecatalogue.BuildConfig;

public class ApiUrl {

    public static final String BASE_URL="https://api.themoviedb.org/3/";
    public static final String POSTER_PATH ="https://image.tmdb.org/t/p/w342/";

    public static final String PATH_MOVIE="discover/movie";
    public static final String PATH_TV="discover/tv";
    public static final String SEARCH_MOVIE="search/movie";
    public static final String SEARCH_TV="search/tv";

    public static final String API_KEY = BuildConfig.TMDB_API_KEY;
    public static final String LANGUAGE_ENGLISH = "en-US";
    public static final String LANGUAGE_INDONESIA = "id-ID";





}
