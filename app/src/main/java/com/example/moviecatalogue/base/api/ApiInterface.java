package com.example.moviecatalogue.base.api;

import android.os.Build;

import com.example.moviecatalogue.model.MovieResult;
import com.example.moviecatalogue.model.TvResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.example.moviecatalogue.base.networks.ApiUrl.PATH_MOVIE;
import static com.example.moviecatalogue.base.networks.ApiUrl.PATH_TV;
import static com.example.moviecatalogue.base.networks.ApiUrl.SEARCH_MOVIE;
import static com.example.moviecatalogue.base.networks.ApiUrl.SEARCH_TV;

public interface ApiInterface {

    @GET(PATH_TV)
    Call<TvResult> getTvShow(@Query("api_key") String apiKey);

    @GET(PATH_MOVIE)
    Call<MovieResult> getMovie(@Query("api_key") String apiKey);

    @GET(SEARCH_MOVIE)
    Call<MovieResult> getSeachMovie(@Query("api_key") String ApiKey,
                                    @Query("language") String language,
                                    @Query("query") String query);

    @GET(SEARCH_TV)
    Call<TvResult> getSeachTv(@Query("api_key") String ApiKey,
                                 @Query("language") String language,
                                 @Query("query") String query);


    @GET(PATH_MOVIE)
    Call<MovieResult> getMovieToday(@Query("api_key") String key,
                                           @Query("primary_release_date.gte") String dateGte,
                                           @Query("primary_release_date.lte") String dateIte);


}
