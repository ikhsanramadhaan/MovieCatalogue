package com.example.moviecatalogue.dbmovie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.moviecatalogue.dbmovie.DbContract.*;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE = DbContract.DB;
    private static final int DATABASE_VERSION = 1;


    public DatabaseHelper(Context context) {
        super(context,DATABASE,null,DATABASE_VERSION );
        SQLiteDatabase database =getWritableDatabase();
        onCreate(database);
    }
    private static final String CREATE_TABLE_MOVIE ="CREATE TABLE IF NOT EXISTS " + DataEntry.TABLE_NAME_MOVIE + " (" +
            DataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DataEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL," +
            DataEntry.COLUMN_VOTE_COUNT_MOVIE + " INTEGER NOT NULL," +
            DataEntry.COLUMN_VOTE_AVERAGE_MOVIE + " DOUBLE NOT NULL," +
            DataEntry.COLUMN_TITLE_MOVIE + " TEXT NOT NULL," +
            DataEntry.COLUMN_POPULARITY_MOVIE + " DOUBLE NOT NULL," +
            DataEntry.COLUMN_POSTER_PATH_MOVIE + " TEXT NOT NULL," +
            DataEntry.COLUMN_BACKDROP_PATH_MOVIE + " TEXT NOT NULL," +
            DataEntry.COLUMN_ORIGINAL_TITLE_MOVIE + " TEXT NOT NULL," +
            DataEntry.COLUMN_OVERVIEW_MOVIE + " TEXT NOT NULL," +
            DataEntry.COLUMN_RELEASE_DATE_MOVIE + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_TV ="CREATE TABLE IF NOT EXISTS " + DataEntry.TABLE_NAME_TV + " (" +
            DataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DataEntry.COLUMN_TV_ID + " INTEGER NOT NULL," +
            DataEntry.COLUMN_OVERVIEW_TV + " TEXT NOT NULL," +
            DataEntry.COLUMN_VOTE_COUNT_TV + " INTEGER NOT NULL," +
            DataEntry.COLUMN_POSTER_PATH_TV + " TEXT NOT NULL," +
            DataEntry.COLUMN_VOTE_AVERAGE_TV + " DOUBLE NOT NULL," +
            DataEntry.COLUMN_ORIGINAL_NAME_TV + " TEXT NOT NULL," +
            DataEntry.COLUMN_POPULARITY_TV + " DOUBLE NOT NULL," +
            DataEntry.COLUMN_BACKDROP_PATH_TV + " TEXT NOT NULL," +
            DataEntry.COLUMN_RELEASE_DATE_TV + " TEXT NOT NULL);";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(CREATE_TABLE_MOVIE);
            sqLiteDatabase.execSQL(CREATE_TABLE_TV);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql_drop_table_movie = "DROP TABLE IF EXISTS " + DataEntry.TABLE_NAME_MOVIE;
        String sql_drop_table_tv = "DROP TABLE IF EXISTS " + DataEntry.TABLE_NAME_TV;
        sqLiteDatabase.execSQL(sql_drop_table_movie);
        sqLiteDatabase.execSQL(sql_drop_table_tv);
        onCreate(sqLiteDatabase);
    }
}
