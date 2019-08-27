package com.example.moviecatalogue.dbmovie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.example.moviecatalogue.dbmovie.DbContract.*;
import com.example.moviecatalogue.model.FavoriteMovie;
import com.example.moviecatalogue.model.Film;

public class MovieCrud {

    private Context context;

    public MovieCrud(Context context) {
        this.context = context;
    }

    // ---------------------------------------------------------------------------------------------
    // Constants projection ini gunanya untuk memilih column pada database
    // Guna variable projection ini sama seperti * pada SQL
    private String projection[] = {DataEntry._ID,
            DataEntry.COLUMN_MOVIE_ID,
            DataEntry.COLUMN_VOTE_COUNT_MOVIE,
            DataEntry.COLUMN_VOTE_AVERAGE_MOVIE,
            DataEntry.COLUMN_TITLE_MOVIE,
            DataEntry.COLUMN_POPULARITY_MOVIE,
            DataEntry.COLUMN_POSTER_PATH_MOVIE,
            DataEntry.COLUMN_BACKDROP_PATH_MOVIE,
            DataEntry.COLUMN_ORIGINAL_TITLE_MOVIE,
            DataEntry.COLUMN_OVERVIEW_MOVIE,
            DataEntry.COLUMN_RELEASE_DATE_MOVIE
    };
    // ---------------------------------------------------------------------------------------------

    // Insert Data dalam DatabaseHelper ------------------------------------------------------------
    public boolean insertDataMovie(Film mMovie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataEntry.COLUMN_MOVIE_ID, mMovie.getId());
        contentValues.put(DataEntry.COLUMN_VOTE_COUNT_MOVIE, mMovie.getVoteCount());
        contentValues.put(DataEntry.COLUMN_VOTE_AVERAGE_MOVIE, mMovie.getVoteAverage());
        contentValues.put(DataEntry.COLUMN_TITLE_MOVIE, mMovie.getTitle());
        contentValues.put(DataEntry.COLUMN_POPULARITY_MOVIE, mMovie.getPopularity());
        contentValues.put(DataEntry.COLUMN_POSTER_PATH_MOVIE, mMovie.getPosterPath());
        contentValues.put(DataEntry.COLUMN_TITLE_MOVIE, mMovie.getOriginalTitle());
        contentValues.put(DataEntry.COLUMN_OVERVIEW_MOVIE, mMovie.getOverview());
        contentValues.put(DataEntry.COLUMN_RELEASE_DATE_MOVIE, mMovie.getReleaseDate());
        Uri resultUri = context.getContentResolver().insert(DataEntry.CONTENT_URI,contentValues);
        return resultUri != null;
    }
    // ---------------------------------------------------------------------------------------------

    // Insert Data dalam DatabaseHelper ------------------------------------------------------------
    public boolean insertDataFavorite(FavoriteMovie mMovie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataEntry.COLUMN_MOVIE_ID, mMovie.getId());
        contentValues.put(DataEntry.COLUMN_VOTE_COUNT_MOVIE, mMovie.getVoteCount());
        contentValues.put(DataEntry.COLUMN_VOTE_AVERAGE_MOVIE, mMovie.getVoteAverage());
        contentValues.put(DataEntry.COLUMN_TITLE_MOVIE, mMovie.getTitle());
        contentValues.put(DataEntry.COLUMN_POPULARITY_MOVIE, mMovie.getPopularity());
        contentValues.put(DataEntry.COLUMN_POSTER_PATH_MOVIE, mMovie.getPosterPath());
        contentValues.put(DataEntry.COLUMN_BACKDROP_PATH_MOVIE, mMovie.getBackdropPath());
        contentValues.put(DataEntry.COLUMN_ORIGINAL_TITLE_MOVIE, mMovie.getOriginalTitle());
        contentValues.put(DataEntry.COLUMN_OVERVIEW_MOVIE, mMovie.getOverview());
        contentValues.put(DataEntry.COLUMN_RELEASE_DATE_MOVIE, mMovie.getReleaseDate());
        Uri resultUri = context.getContentResolver().insert(DataEntry.CONTENT_URI,contentValues);
        return resultUri != null;
    }
    // ---------------------------------------------------------------------------------------------


    public boolean deleteDataId(String mID) {
        String selection = DataEntry.COLUMN_MOVIE_ID + " = '" + mID + "'";
        int resultUri = context.getContentResolver().delete(
                DataEntry.CONTENT_URI,
                selection,
                null);
        return resultUri != 0;
    }

    public Cursor getFavoriteMovieId(String mID){
        String selection = DataEntry.COLUMN_MOVIE_ID + " = '" + mID + "'";
        return context.getContentResolver().query(
                DataEntry.CONTENT_URI,
                projection,
                selection,
                null,
                null);
    }

    // Method Cursor untuk menarik semua data sementara dari database ------------------------------
    // dalam method ini menggambil semua data tanpa arguments
    public Cursor getFavoriteMovie(){
        return context.getContentResolver().query(
                DataEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }
}
