package com.example.moviecatalogue.dbmovie;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class TvProvider extends ContentProvider {
    public static final String LOG_TAG = TvProvider.class.getSimpleName();
    private static final int DATA = 100; // Projection All
    private static final int DATA_ID = 101; // Projection ID
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private DatabaseHelper mDbHelper;

    static {
        sUriMatcher.addURI(DbContract.CONTENT_AUTHORITY, DbContract.PATH_TV, DATA);
        sUriMatcher.addURI(DbContract.CONTENT_AUTHORITY, DbContract.PATH_TV + "/#", DATA_ID);
    }
    @Override
    public boolean onCreate() {
        mDbHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase(); // Get readable database
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case DATA:
                cursor = database.query(DbContract.DataEntry.TABLE_NAME_TV, strings, s,
                        strings1, null,null,s1); // Select Semua Data
                break;
            case DATA_ID:

                s = DbContract.DataEntry.COLUMN_TV_ID + "=?"; // Where ID = "?"
                strings1 = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(DbContract.DataEntry.TABLE_NAME_TV, strings, s, strings1,
                        null, null, s1);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case DATA:
                return DbContract.DataEntry.CONTENT_LIST_TYPE_TV;
            case DATA_ID:
                return DbContract.DataEntry.CONTENT_ITEM_TYPE_TV;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case DATA :
                return insertData(uri, contentValues);
            default :
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertData(Uri uri, ContentValues values){
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(DbContract.DataEntry.TABLE_NAME_TV, null , values);

        if (id ==-1){
            Log.e(LOG_TAG, "Failed to insert row for "+uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsDeleted;
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case DATA:
                rowsDeleted = database.delete(DbContract.DataEntry.TABLE_NAME_TV, s, strings);
                break;
            case DATA_ID:
                s = DbContract.DataEntry.COLUMN_TV_ID + "=?";
                strings = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(DbContract.DataEntry.TABLE_NAME_TV, s, strings);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        if (rowsDeleted != 0) { getContext().getContentResolver().notifyChange(uri, null); }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case DATA :
                return updateData(uri, contentValues, s, strings);
            case DATA_ID :
                s = DbContract.DataEntry._ID + "=?";
                strings = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return  updateData(uri, contentValues, s, strings);
            default :
                throw new IllegalArgumentException("Update is not Supported");
        }
    }

    private int updateData(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        if (values.size() == 0){
            return 0;
        }
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowUpdated = database.update(DbContract.DataEntry.TABLE_NAME_TV, values, selection, selectionArgs);
        if (rowUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowUpdated;
    }
}
