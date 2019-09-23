package com.example.moviecatalogue.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.model.Film;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutionException;

import static com.example.moviecatalogue.base.networks.ApiUrl.POSTER_PATH;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.CONTENT_URI_MOVIE;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.MOVIE;

public class StackRemoteFactory implements RemoteViewsService.RemoteViewsFactory {
    private final Context mContext;
    private Cursor cursor;
    int mAppWidgetId;

    public StackRemoteFactory(Context mContext,  Intent intent) {
        this.mContext = mContext;
        int mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        cursor = mContext.getContentResolver().query(
                CONTENT_URI_MOVIE, null, null, null, null
        );
    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        cursor = mContext.getContentResolver().query(CONTENT_URI_MOVIE, null, null, null, null);
        Binder.restoreCallingIdentity(identityToken);
    }

    private Film getFav(int position) {
        if (!cursor.moveToPosition(position)) {
            Log.d("Position invalid!", "getFav: " + position);
            throw new IllegalStateException("Position invalid!");

        }
        return new Film(cursor);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        final RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        String posterPath = POSTER_PATH + getFav(i).getPosterPath();
        Bitmap bmp= null;
        try {
            bmp = Glide.with(mContext).asBitmap()
                    .load(posterPath)
                    .apply(new RequestOptions().fitCenter())
                    .submit()
                    .get();
            rv.setImageViewBitmap(R.id.image_view, bmp);
        }catch (InterruptedException | ExecutionException e){
            Log.d("Widget Load Error", "error");
        }

        Bundle extras = new Bundle();
        extras.putInt(FavoriteWidget.EXTRA_ITEM, i);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.image_view, fillIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return cursor.moveToPosition(i) ? cursor.getLong(0) : i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
