package com.example.moviecatalogue.dbmovie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.moviecatalogue.model.FavoriteMovie;
import com.example.moviecatalogue.model.FavoriteTv;
import com.example.moviecatalogue.model.Film;
import com.example.moviecatalogue.model.TvShow;

public class TvCrud {

    private Context context;

    public TvCrud(Context context) {
        this.context = context;
    }

    String projection[] ={DbContract.DataEntry._ID,
            DbContract.DataEntry.COLUMN_TV_ID,
            DbContract.DataEntry.COLUMN_ORIGINAL_NAME_TV,
            DbContract.DataEntry.COLUMN_RELEASE_DATE_TV,
            DbContract.DataEntry.COLUMN_OVERVIEW_TV,
            DbContract.DataEntry.COLUMN_BACKDROP_PATH_TV,
            DbContract.DataEntry.COLUMN_POSTER_PATH_TV,
            DbContract.DataEntry.COLUMN_POPULARITY_TV,
            DbContract.DataEntry.COLUMN_VOTE_AVERAGE_TV,
            DbContract.DataEntry.COLUMN_VOTE_COUNT_TV
    };

    public boolean insertDataMovie(TvShow tvShow) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContract.DataEntry.COLUMN_TV_ID, tvShow.getId());
        contentValues.put(DbContract.DataEntry.COLUMN_ORIGINAL_NAME_TV, tvShow.getOriginal_name());
        contentValues.put(DbContract.DataEntry.COLUMN_RELEASE_DATE_TV, tvShow.getFirst_air_date());
        contentValues.put(DbContract.DataEntry.COLUMN_OVERVIEW_TV, tvShow.getOverview());
        contentValues.put(DbContract.DataEntry.COLUMN_BACKDROP_PATH_TV, tvShow.getBackdrop_path());
        contentValues.put(DbContract.DataEntry.COLUMN_POSTER_PATH_TV, tvShow.getPoster_path());
        contentValues.put(DbContract.DataEntry.COLUMN_POPULARITY_TV, tvShow.getPopularity());
        contentValues.put(DbContract.DataEntry.COLUMN_VOTE_AVERAGE_TV, tvShow.getVoteAverage());
        contentValues.put(DbContract.DataEntry.COLUMN_VOTE_COUNT_TV, tvShow.getVote_count());
        Uri resultUri = context.getContentResolver().insert(DbContract.DataEntry.CONTENT_URI,contentValues);
        return resultUri != null;
    }

    public boolean insertDataFavorite(FavoriteTv tvshow) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContract.DataEntry.COLUMN_TV_ID, tvshow.getId());
        contentValues.put(DbContract.DataEntry.COLUMN_ORIGINAL_NAME_TV, tvshow.getOriginal_name());
        contentValues.put(DbContract.DataEntry.COLUMN_RELEASE_DATE_TV, tvshow.getFirst_air_date());
        contentValues.put(DbContract.DataEntry.COLUMN_OVERVIEW_TV, tvshow.getOverview());
        contentValues.put(DbContract.DataEntry.COLUMN_BACKDROP_PATH_TV, tvshow.getBackdrop_path());
        contentValues.put(DbContract.DataEntry.COLUMN_POSTER_PATH_TV, tvshow.getPoster_path());
        contentValues.put(DbContract.DataEntry.COLUMN_POPULARITY_TV, tvshow.getPopularity());
        contentValues.put(DbContract.DataEntry.COLUMN_VOTE_AVERAGE_TV, tvshow.getVoteAverage());
        contentValues.put(DbContract.DataEntry.COLUMN_VOTE_COUNT_TV, tvshow.getVote_count());
        Uri resultUri = context.getContentResolver().insert(DbContract.DataEntry.CONTENT_URI,contentValues);
        return resultUri != null;
    }

    public boolean deleteDataId(String mID) {
        String selection = DbContract.DataEntry.COLUMN_TV_ID + " = '" + mID + "'";
        int resultUri = context.getContentResolver().delete(
                DbContract.DataEntry.CONTENT_URI,
                selection,
                null);
        return resultUri != 0;
    }

    public Cursor getFavoriteTvId(String mID){
        String selection = DbContract.DataEntry.COLUMN_TV_ID + " = '" + mID + "'";
        return context.getContentResolver().query(
                DbContract.DataEntry.CONTENT_URI,
                projection,
                selection,
                null,
                null);
    }

    // Method Cursor untuk menarik semua data sementara dari database ------------------------------
    // dalam method ini menggambil semua data tanpa arguments
    public Cursor getFavoriteTv(){
        return context.getContentResolver().query(
                DbContract.DataEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }



}
