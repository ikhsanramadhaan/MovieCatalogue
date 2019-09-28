package com.example.moviecatalogue.view.activity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.dbmovie.movie.FavoriteMovieHelper;
import com.example.moviecatalogue.model.Film;
import com.example.moviecatalogue.widget.FavoriteWidget;
import com.squareup.picasso.Picasso;

import static android.provider.BaseColumns._ID;
import static com.example.moviecatalogue.base.networks.ApiUrl.POSTER_PATH;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.CONTENT_URI_MOVIE;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteMovie.COLUMN_OVERVIEW_MOVIE;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteMovie.COLUMN_POPULARITY_MOVIE;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteMovie.COLUMN_POSTER_PATH_MOVIE;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteMovie.COLUMN_RELEASE_DATE_MOVIE;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteMovie.COLUMN_TITLE_MOVIE;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteMovie.COLUMN_VOTE_AVERAGE_MOVIE;

public class DetailMovieActivity extends AppCompatActivity {
    public static final String EXTRA_FILM = "extra_film";
    private FavoriteMovieHelper helper;
    private boolean isFavorite = false;
    private Menu mMenu = null;
    private int movieId;
    private Film film;
    private String  judul, tahun, deskripsi,poster;
    private Double rating, popularity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        setTitle(R.string.detail_movie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        helper = FavoriteMovieHelper.getInstance(getApplicationContext());
        helper.Open();

        ImageView imageView = findViewById(R.id.photo);
        TextView tvJudul = findViewById(R.id.judul);
        TextView tvTahun = findViewById(R.id.rilis);
        TextView tvRating = findViewById(R.id.rating);
        TextView tvPopularity = findViewById(R.id.popularity);
        TextView tvDeskripsi = findViewById(R.id.description);

        getExtraData();
        Picasso.get().load(POSTER_PATH + poster).into(imageView);
        tvJudul.setText(judul);
        tvTahun.setText(tahun);
        tvRating.setText(String.valueOf(rating));
        tvPopularity.setText(String.valueOf(popularity));
        tvDeskripsi.setText(deskripsi);

        loadMovie();
    }

    public void getExtraData(){
        film = getIntent().getParcelableExtra(EXTRA_FILM);
        if (film != null) {
            movieId = film.getId();
            judul = film.getTitle();
            tahun = film.getReleaseDate();
            deskripsi = film.getOverview();
            rating = film.getVoteAverage();
            popularity = film.getPopularity();
            poster = film.getPosterPath();
        }
    }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.menu_favorite, menu);
            mMenu = menu;
            setIconFavorite();
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected (@NonNull MenuItem item){
            int id = item.getItemId();
            if (id == android.R.id.home) {
                finish();
            } else if (id == R.id.menu_favorite) {
                if (isFavorite) {
                    removeFavorite();
                    Toast.makeText(DetailMovieActivity.this, "Satu item berhasil dihapus", Toast.LENGTH_SHORT).show();
                } else {
                    addFavorite();
                }
                isFavorite = !isFavorite;
                setIconFavorite();
                updateWidget();
            }
            return super.onOptionsItemSelected(item);
        }

    private void setIconFavorite(){
        if (isFavorite) {
            mMenu.getItem(0).setIcon(R.drawable.ic_delete);
        } else {
            mMenu.getItem(0).setIcon(R.drawable.ic_favorite);
        }
    }


    private void loadMovie() {
        FavoriteMovieHelper movieHelper = FavoriteMovieHelper.getInstance(getApplicationContext());
        movieHelper.Open();
        Cursor cursor = getContentResolver().query(
                Uri.parse(CONTENT_URI_MOVIE + "/" + film.getId()),
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) isFavorite = true;
            cursor.close();
        }
    }

    private void addFavorite() {
        ContentValues contentValues = new ContentValues();

        contentValues.put(_ID, film.getId());
        contentValues.put(COLUMN_TITLE_MOVIE, film.getTitle());
        contentValues.put(COLUMN_POSTER_PATH_MOVIE, film.getPosterPath());
        contentValues.put(COLUMN_OVERVIEW_MOVIE, film.getOverview());
        contentValues.put(COLUMN_VOTE_AVERAGE_MOVIE, film.getVoteAverage());
        contentValues.put(COLUMN_RELEASE_DATE_MOVIE, film.getReleaseDate());
        contentValues.put(COLUMN_POPULARITY_MOVIE, film.getPopularity());

        getContentResolver().insert(CONTENT_URI_MOVIE, contentValues);

        Toast.makeText(this,"Berhasil", Toast.LENGTH_LONG).show();
    }

    private void removeFavorite() {

        getContentResolver().delete(
                Uri.parse(CONTENT_URI_MOVIE + "/" + film.getId()),
                null,
                null
        );
        Toast.makeText(this, "berhasil di hapus", Toast.LENGTH_LONG).show();
    }
    private void updateWidget() {
        try {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
            ComponentName componentName = new ComponentName(getApplicationContext(), FavoriteWidget.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view);
        }catch (IllegalStateException e){
            Log.d("DETAIL", "updateWidget: "+e.getMessage());
        }

    }
}


