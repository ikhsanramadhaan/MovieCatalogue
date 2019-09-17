package com.example.moviecatalogue.dbmovie.movie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.moviecatalogue.model.Film;
import com.example.moviecatalogue.model.TvShow;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static android.provider.BaseColumns._ID;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteMovie.COLUMN_MOVIEID;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteMovie.COLUMN_OVERVIEW_MOVIE;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteMovie.COLUMN_POPULARITY_MOVIE;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteMovie.COLUMN_POSTER_PATH_MOVIE;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteMovie.COLUMN_RELEASE_DATE_MOVIE;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteMovie.COLUMN_TITLE_MOVIE;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteMovie.COLUMN_VOTE_AVERAGE_MOVIE;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteMovie.TABLE_MOVIE;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.COLUMN_ORIGINAL_NAME_TV;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.COLUMN_OVERVIEW_TV;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.COLUMN_POPULARITY_TV;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.COLUMN_POSTER_PATH_TV;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.COLUMN_RELEASE_DATE_TV;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.COLUMN_TV_ID;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.COLUMN_VOTE_AVERAGE_TV;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.TABLE_TV;

public class FavoriteMovieHelper {
    private static DatabaseHelper databaseHelper;
    private static FavoriteMovieHelper INSTANCE;

    private SQLiteDatabase database;

    public FavoriteMovieHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteMovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteMovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void Open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    Cursor queryByIdProvider(String id) {
        return database.query(TABLE_MOVIE, null
                , _ID   + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    Cursor queryProvider() {
        return database.query(TABLE_MOVIE
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC");
    }

    long insertProvider(ContentValues values) {
        return database.insert(TABLE_MOVIE, null, values);
    }

    int updateProvider(String id, ContentValues values) {
        return database.update(TABLE_MOVIE, values, _ID + " =?", new String[]{id});
    }

    int deleteProvider(String id) {
        return database.delete(TABLE_MOVIE, _ID + " = ?", new String[]{id});
    }




}
