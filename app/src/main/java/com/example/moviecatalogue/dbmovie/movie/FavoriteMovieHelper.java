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

    public ArrayList<Film> getAllMovie() {
        ArrayList<Film> arrayList = new ArrayList<>();
        Cursor cursor = database.query(TABLE_MOVIE, null,
                null,
                null,
                null,
                null,
                COLUMN_MOVIEID + " ASC",
                null);
        cursor.moveToFirst();
        Film movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Film();
                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_MOVIEID))));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE_MOVIE)));
                movie.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_VOTE_AVERAGE_MOVIE))));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(COLUMN_POSTER_PATH_MOVIE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(DbContract.FavoriteMovie.COLUMN_OVERVIEW_MOVIE)));
                movie.setPopularity(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_POPULARITY_MOVIE))));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(COLUMN_RELEASE_DATE_MOVIE)));

                arrayList.add(movie);

                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public void insertMovie(Film film) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MOVIEID,film.getId());
        contentValues.put(COLUMN_VOTE_AVERAGE_MOVIE, film.getVoteAverage());
        contentValues.put(COLUMN_TITLE_MOVIE, film.getTitle());
        contentValues.put(COLUMN_OVERVIEW_MOVIE, film.getOverview());
        contentValues.put(COLUMN_POPULARITY_MOVIE, film.getPopularity());
        contentValues.put(COLUMN_POSTER_PATH_MOVIE, film.getPosterPath());
        contentValues.put(COLUMN_RELEASE_DATE_MOVIE, film.getReleaseDate());
        database.insert(TABLE_MOVIE, null, contentValues);

    }

    public void deleteMovie(int id) {
        database = databaseHelper.getWritableDatabase();
        database.delete(TABLE_MOVIE, COLUMN_MOVIEID + "=" + id, null);
    }

    public boolean checkMovie(String id) {
        database = databaseHelper.getWritableDatabase();
        String selectString = "SELECT * FROM " + TABLE_MOVIE + " WHERE " + COLUMN_MOVIEID + " =?";
        Cursor cursor = database.rawQuery(selectString, new String[]{id});
        boolean checkMovie = false;
        if (cursor.moveToFirst()) {
            checkMovie = true;
            int count = 0;
            while (cursor.moveToNext()) {
                count++;
            }
            Log.d(TAG, String.format("%d records found", count));
        }
        cursor.close();
        return checkMovie;
    }




}
