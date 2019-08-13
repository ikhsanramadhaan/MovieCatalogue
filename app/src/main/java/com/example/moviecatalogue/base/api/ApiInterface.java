package com.example.moviecatalogue.base.api;

import com.example.moviecatalogue.model.MovieResult;
import com.example.moviecatalogue.model.TvResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.example.moviecatalogue.base.networks.ApiUrl.PATH_MOVIE;
import static com.example.moviecatalogue.base.networks.ApiUrl.PATH_TV;

public interface ApiInterface {

    @GET(PATH_TV)
    Call<TvResult> getTvShow(@Query("api_key") String apiKey);

    @GET(PATH_MOVIE)
    Call<MovieResult> getMovie(@Query("api_key") String apiKey);

}
