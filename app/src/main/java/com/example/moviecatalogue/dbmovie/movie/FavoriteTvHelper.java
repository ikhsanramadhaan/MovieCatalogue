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
import static android.provider.BaseColumns._ID;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.COLUMN_TV_ID;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.COLUMN_ORIGINAL_NAME_TV;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.COLUMN_OVERVIEW_TV;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.COLUMN_POPULARITY_TV;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.COLUMN_POSTER_PATH_TV;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.COLUMN_RELEASE_DATE_TV;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.COLUMN_VOTE_AVERAGE_TV;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.TABLE_TV;

public class FavoriteTvHelper {
    private DatabaseHelper helper;
    private static FavoriteTvHelper INSTANCE;
    private static SQLiteDatabase database;

    public FavoriteTvHelper(Context context) {
        helper = new DatabaseHelper(context);
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

    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
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
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        TvShow tv;
        if (cursor.getCount() > 0) {
            do {
                tv = new TvShow();
                tv.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_TV_ID))));
                tv.setOriginal_name(cursor.getString(cursor.getColumnIndex(COLUMN_ORIGINAL_NAME_TV)));
                tv.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_VOTE_AVERAGE_TV))));
                tv.setPoster_path(cursor.getString(cursor.getColumnIndex(COLUMN_POSTER_PATH_TV)));
                tv.setOverview(cursor.getString(cursor.getColumnIndex(COLUMN_OVERVIEW_TV)));
                tv.setPopularity(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_POPULARITY_TV))));
                tv.setFirst_air_date(cursor.getString(cursor.getColumnIndex(COLUMN_RELEASE_DATE_TV)));

                arrayList.add(tv);

                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertTv(TvShow tv) {
        ContentValues args = new ContentValues();
        args.put(COLUMN_TV_ID, tv.getId());
        args.put(COLUMN_ORIGINAL_NAME_TV, tv.getOriginal_name());
        args.put(COLUMN_OVERVIEW_TV, tv.getOverview());
        args.put(COLUMN_POSTER_PATH_TV, tv.getPoster_path());
        args.put(COLUMN_VOTE_AVERAGE_TV, tv.getVoteAverage());
        args.put(COLUMN_POPULARITY_TV, tv.getPopularity());
        args.put(COLUMN_RELEASE_DATE_TV, tv.getFirst_air_date());
        return database.insert(TABLE_TV, null, args);
    }

    public void deleteTv(int id) {
        database = helper.getWritableDatabase();
        database.delete(TABLE_TV, _ID + "=" + id, null);
    }

    public boolean checkTv(String id) {
        database = helper.getWritableDatabase();
        String selectString = "SELECT * FROM " + TABLE_TV + " WHERE " + _ID + " =?";
        Cursor cursor = database.rawQuery(selectString, new String[]{id});
        boolean checkTv = false;
        if (cursor.moveToFirst()) {
            checkTv = true;
            int count = 0;
            while (cursor.moveToNext()) {
                count++;
            }
            Log.d(TAG, String.format("%d records found", count));
        }
        cursor.close();
        return checkTv;
    }
}
