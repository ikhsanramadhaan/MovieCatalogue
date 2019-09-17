package com.example.moviecatalogue.dbmovie.movie;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import java.util.Objects;

import static com.example.moviecatalogue.dbmovie.movie.DbContract.AUTHORITY;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.CONTENT_URI_MOVIE;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.CONTENT_URI_TV;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteMovie.TABLE_MOVIE;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.TABLE_TV;

public class MyProvider extends ContentProvider {

    private static final int MOVIE = 100;
    private static final int MOVIE_ID = 101;

    private static final int TV = 200;
    private static final int TV_ID = 201;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE, MOVIE);

        sUriMatcher.addURI(AUTHORITY,
                TABLE_MOVIE + "/#",
                MOVIE_ID);
    }

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_TV, TV);

        sUriMatcher.addURI(AUTHORITY,
                TABLE_TV + "/#",
                TV_ID);
    }

    private FavoriteTvHelper favoriteTvHelper;
    private FavoriteMovieHelper favoriteMovieHelper;
    @Override
    public boolean onCreate() {
        favoriteMovieHelper = new FavoriteMovieHelper(getContext());
        favoriteMovieHelper.Open();
        favoriteTvHelper = new FavoriteTvHelper(getContext());
        favoriteTvHelper.Open();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = favoriteMovieHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = favoriteMovieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            case TV:
                cursor = favoriteTvHelper.queryProvider();
                break;
            case TV_ID:
                cursor = favoriteTvHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        if (cursor != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);
            }
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        favoriteMovieHelper.Open();
        favoriteTvHelper.Open();
        long added;
        Uri contentUri = null;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                added = favoriteMovieHelper.insertProvider(contentValues);
                if (added > 0) {
                    contentUri = ContentUris.withAppendedId(CONTENT_URI_MOVIE, added);
                }
                break;
            case TV:
                added = favoriteTvHelper.insertProvider(contentValues);
                if (added > 0) {
                    contentUri = ContentUris.withAppendedId(CONTENT_URI_TV, added);
                }
                break;
            default:
                added = 0;
                break;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return contentUri;

    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                deleted = favoriteMovieHelper.deleteProvider(uri.getLastPathSegment());
                break;
            case TV_ID:
                deleted = favoriteTvHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
            }
        }
        return deleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updated;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                updated = favoriteMovieHelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;
            case TV_ID:
                updated = favoriteTvHelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
            }
        }
        return updated;
    }
}
