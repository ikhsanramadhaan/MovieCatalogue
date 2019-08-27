package com.example.moviecatalogue.dbmovie;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class DbContract {
    public DbContract() {
    }

    public static final String DB = "moviedb.db"; // Nama Database
    public static final String CONTENT_AUTHORITY = "com.example.moviecatalogue"; // Nama Domain Aplikasi
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "data_movie"; // Isinya sama dengan Table Name
    public static final String PATH_TV = "data_tv"; // Isinya sama dengan Table Name
    // ---------------------------------------------------------------------------------------------

    // Deklarasi Elemen Table ----------------------------------------------------------------------
    public static final class DataEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_MOVIE);
        // -----------------------------------------------------------------------------------------
        public static final String TABLE_NAME_MOVIE = "data_movie"; // Deklarasi Nama Table

        //movie db
        public static final String _ID  = BaseColumns._ID; // Dekalarasi ID
        public static final String COLUMN_MOVIE_ID = "id";
        public static final String COLUMN_VOTE_COUNT_MOVIE = "voteCount";
        public static final String COLUMN_VOTE_AVERAGE_MOVIE = "voteAverage";
        public static final String COLUMN_TITLE_MOVIE = "title";
        public static final String COLUMN_POPULARITY_MOVIE = "popularity";
        public static final String COLUMN_POSTER_PATH_MOVIE = "posterPath";
        public static final String COLUMN_BACKDROP_PATH_MOVIE = "backdropPath";
        public static final String COLUMN_ORIGINAL_TITLE_MOVIE = "originalTitle";
        public static final String COLUMN_OVERVIEW_MOVIE = "overview";
        public static final String COLUMN_RELEASE_DATE_MOVIE = "releaseDate";
        // -----------------------------------------------------------------------------------------

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final Uri CONTENT_URI_TV = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TV);
        public static final String TABLE_NAME_TV = "data_tv"; // Deklarasi Nama Table

        public static final String COLUMN_TV_ID = "id";
        public static final String COLUMN_ORIGINAL_NAME_TV = "original_name";
        public static final String COLUMN_VOTE_COUNT_TV = "vote_count";
        public static final String COLUMN_OVERVIEW_TV = "overview";
        public static final String COLUMN_POSTER_PATH_TV = "poster_path";
        public static final String COLUMN_RELEASE_DATE_TV = "first_air_date";
        public static final String COLUMN_BACKDROP_PATH_TV = "backdrop_path";
        public static final String COLUMN_VOTE_AVERAGE_TV = "voteAverage";
        public static final String COLUMN_POPULARITY_TV = "popularity";

        public static final String CONTENT_LIST_TYPE_TV = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV;
        public static final String CONTENT_ITEM_TYPE_TV = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TV;

    }
    // ---------------------------------------------------------------------------------------------

}
