package com.example.moviecatalogue.dbmovie.movie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteMovie.TABLE_MOVIE;

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
