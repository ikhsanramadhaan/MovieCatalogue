package com.example.moviecatalogue.dbmovie.movie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.moviecatalogue.model.TvShow;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.COLUMN_ORIGINAL_NAME_TV;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.COLUMN_OVERVIEW_TV;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.COLUMN_POPULARITY_TV;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.COLUMN_POSTER_PATH_TV;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.COLUMN_RELEASE_DATE_TV;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.COLUMN_TV_ID;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.COLUMN_VOTE_AVERAGE_TV;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.TABLE_TV;

public class FavoriteTvHelper {
    private static DatabaseHelper databaseHelper;
    private static FavoriteTvHelper INSTANCE;
    private SQLiteDatabase database;

    public FavoriteTvHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteTvHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteTvHelper(context);
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

    public ArrayList<TvShow> getAllTv() {
        ArrayList<TvShow> arrayList = new ArrayList<>();
        Cursor cursor = database.query(TABLE_TV, null,
                null,
                null,
                null,
                null,
                COLUMN_TV_ID + " ASC",
                null);
        cursor.moveToFirst();
        TvShow tvShow;
        if (cursor.getCount() > 0) {
            do {
                tvShow = new TvShow();
                tvShow.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_TV_ID))));
                tvShow.setOriginal_name(cursor.getString(cursor.getColumnIndex(COLUMN_ORIGINAL_NAME_TV)));
                tvShow.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_VOTE_AVERAGE_TV))));
                tvShow.setPoster_path(cursor.getString(cursor.getColumnIndex(COLUMN_POSTER_PATH_TV)));
                tvShow.setOverview(cursor.getString(cursor.getColumnIndex(COLUMN_OVERVIEW_TV)));
                tvShow.setPopularity(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_POPULARITY_TV))));
                tvShow.setFirst_air_date(cursor.getString(cursor.getColumnIndex(COLUMN_RELEASE_DATE_TV)));

                arrayList.add(tvShow);

                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public void insertTv(TvShow tvShow) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TV_ID,tvShow.getId());
        contentValues.put(COLUMN_VOTE_AVERAGE_TV, tvShow.getVoteAverage());
        contentValues.put(COLUMN_ORIGINAL_NAME_TV, tvShow.getOriginal_name());
        contentValues.put(COLUMN_OVERVIEW_TV, tvShow.getOverview());
        contentValues.put(COLUMN_POPULARITY_TV, tvShow.getPopularity());
        contentValues.put(COLUMN_POSTER_PATH_TV, tvShow.getPoster_path());
        contentValues.put(COLUMN_RELEASE_DATE_TV, tvShow.getFirst_air_date());
        database.insert(TABLE_TV, null, contentValues);
    }

    public void deleteTv(int id) {
        database = databaseHelper.getWritableDatabase();
        database.delete(TABLE_TV, COLUMN_TV_ID + "=" + id, null);
    }

    public boolean checkTv(String id) {
        database = databaseHelper.getWritableDatabase();
        String selectString = "SELECT * FROM " + TABLE_TV + " WHERE " + COLUMN_TV_ID + " =?";
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
