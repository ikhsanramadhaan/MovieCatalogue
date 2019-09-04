package com.example.moviecatalogue.dbmovie.movie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE = "moviedb";
    private static final int DATABASE_VERSION = 4;


    public DatabaseHelper(Context context) {
        super(context,DATABASE,null,DATABASE_VERSION );
        SQLiteDatabase database = getWritableDatabase();
        onCreate(database);
    }


    private static final String CREATE_TABLE_MOVIE ="CREATE TABLE IF NOT EXISTS " + DbContract.FavoriteMovie.TABLE_MOVIE + " (" +
            DbContract.FavoriteMovie._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DbContract.FavoriteMovie.COLUMN_MOVIEID + " INTEGER NOT NULL," +
            DbContract.FavoriteMovie.COLUMN_VOTE_AVERAGE_MOVIE + " DOUBLE NOT NULL," +
            DbContract.FavoriteMovie.COLUMN_TITLE_MOVIE + " TEXT NOT NULL," +
            DbContract.FavoriteMovie.COLUMN_POPULARITY_MOVIE + " DOUBLE NOT NULL," +
            DbContract.FavoriteMovie.COLUMN_POSTER_PATH_MOVIE + " TEXT NOT NULL," +
            DbContract.FavoriteMovie.COLUMN_OVERVIEW_MOVIE + " TEXT NOT NULL," +
            DbContract.FavoriteMovie.COLUMN_RELEASE_DATE_MOVIE + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_TV ="CREATE TABLE IF NOT EXISTS " + DbContract.FavoriteTv.TABLE_TV + " (" +
            DbContract.FavoriteTv._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DbContract.FavoriteTv.COLUMN_TV_ID + " INTEGER NOT NULL," +
            DbContract.FavoriteTv.COLUMN_OVERVIEW_TV + " TEXT NOT NULL," +
            DbContract.FavoriteTv.COLUMN_POSTER_PATH_TV + " TEXT NOT NULL," +
            DbContract.FavoriteTv.COLUMN_VOTE_AVERAGE_TV + " DOUBLE NOT NULL," +
            DbContract.FavoriteTv.COLUMN_ORIGINAL_NAME_TV + " TEXT NOT NULL," +
            DbContract.FavoriteTv.COLUMN_POPULARITY_TV + " DOUBLE NOT NULL," +
            DbContract.FavoriteTv.COLUMN_RELEASE_DATE_TV + " TEXT NOT NULL);";


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_MOVIE);
        sqLiteDatabase.execSQL(CREATE_TABLE_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql_drop_table_movie = "DROP TABLE IF EXISTS " + DbContract.FavoriteMovie.TABLE_MOVIE;
        String sql_drop_table_tv = "DROP TABLE IF EXISTS " + DbContract.FavoriteTv.TABLE_TV;
        sqLiteDatabase.execSQL(sql_drop_table_movie);
        sqLiteDatabase.execSQL(sql_drop_table_tv);
        onCreate(sqLiteDatabase);
    }
}
