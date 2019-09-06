package com.example.moviecatalogue.dbmovie.movie;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

class  DbContract {
    public DbContract() {
    }

    static final class FavoriteMovie implements BaseColumns {
        static final String TABLE_MOVIE = "movie_data"; // Deklarasi Nama Table
        static final String COLUMN_MOVIEID = "movieid";
        static final String COLUMN_VOTE_AVERAGE_MOVIE = "voteAverage";
        static final String COLUMN_TITLE_MOVIE = "title";
        static final String COLUMN_POPULARITY_MOVIE = "popularity";
        static final String COLUMN_POSTER_PATH_MOVIE = "posterPath";
        static final String COLUMN_OVERVIEW_MOVIE = "overview";
        static final String COLUMN_RELEASE_DATE_MOVIE = "releaseDate";
    }


        // -----------------------------------------------------------------------------------------
    static final class FavoriteTv implements BaseColumns {
            static final String TABLE_TV = "tv_data";
            static final String COLUMN_TV_ID = "tv_id";
            static final String COLUMN_ORIGINAL_NAME_TV = "original_name";
            static final String COLUMN_OVERVIEW_TV = "overview";
            static final String COLUMN_POSTER_PATH_TV = "poster_path";
            static final String COLUMN_RELEASE_DATE_TV = "first_air_date";
            static final String COLUMN_VOTE_AVERAGE_TV = "voteAverage";
            static final String COLUMN_POPULARITY_TV = "popularity";
        }

    }

