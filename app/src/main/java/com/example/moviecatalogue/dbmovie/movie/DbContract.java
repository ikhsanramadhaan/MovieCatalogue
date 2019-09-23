package com.example.moviecatalogue.dbmovie.movie;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteMovie.TABLE_MOVIE;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.TABLE_TV;

public class  DbContract {

    public static final String AUTHORITY = "com.example.moviecatalogue";
    private static final String SCHEME = "content";

    public static final String TVSHOW = "TvShow";
    public static final String MOVIE = "Movie";
    public DbContract() {
    }
    public static final Uri CONTENT_URI_TV = new Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_TV)
            .build();

    public static final Uri CONTENT_URI_MOVIE = new Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
    public static double getColumnDouble(Cursor cursor, String columnName) {
        return cursor.getDouble(cursor.getColumnIndex(columnName));
    }
    public static final class FavoriteMovie implements BaseColumns {
        public static final String TABLE_MOVIE = "movie_data"; // Deklarasi Nama Table
        public static final String COLUMN_MOVIEID = "movieid";
        public static final String COLUMN_VOTE_AVERAGE_MOVIE = "voteAverage";
        public static final String COLUMN_TITLE_MOVIE = "title";
        public static final String COLUMN_POPULARITY_MOVIE = "popularity";
        public static final String COLUMN_POSTER_PATH_MOVIE = "posterPath";
        public static final String COLUMN_OVERVIEW_MOVIE = "overview";
        public static final String COLUMN_RELEASE_DATE_MOVIE = "releaseDate";

    }


        // -----------------------------------------------------------------------------------------
    public static final class FavoriteTv implements BaseColumns {
            public static final String TABLE_TV = "tv_data";
            public  static final String COLUMN_TV_ID = "tv_id";
            public static final String COLUMN_ORIGINAL_NAME_TV = "original_name";
            public static final String COLUMN_OVERVIEW_TV = "overview";
            public static final String COLUMN_POSTER_PATH_TV = "poster_path";
            public  static final String COLUMN_RELEASE_DATE_TV = "first_air_date";
            public  static final String COLUMN_VOTE_AVERAGE_TV = "voteAverage";
            public  static final String COLUMN_POPULARITY_TV = "popularity";

        }

    }

