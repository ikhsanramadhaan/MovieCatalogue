package com.example.moviecatalogue.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.dbmovie.movie.FavoriteMovieHelper;
import com.example.moviecatalogue.dbmovie.movie.FavoriteTvHelper;
import com.example.moviecatalogue.model.TvShow;
import com.squareup.picasso.Picasso;

import static android.provider.BaseColumns._ID;
import static com.example.moviecatalogue.base.networks.ApiUrl.POSTER_PATH;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.CONTENT_URI_TV;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.COLUMN_ORIGINAL_NAME_TV;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.COLUMN_OVERVIEW_TV;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.COLUMN_POPULARITY_TV;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.COLUMN_POSTER_PATH_TV;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.COLUMN_RELEASE_DATE_TV;
import static com.example.moviecatalogue.dbmovie.movie.DbContract.FavoriteTv.COLUMN_VOTE_AVERAGE_TV;


public class DetailTvActivity extends AppCompatActivity {
    public static final String EXTRA_TV = "extra_tv";
    private FavoriteTvHelper helper;
    private boolean isFavorite = false;
    private Menu mMenu = null;
    private TvShow tvShow;
    private int movieId;
    private String  judul, tahun, deskripsi,poster;
    private Double rating, popularity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);

        setTitle(R.string.detail_movie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        helper = FavoriteTvHelper.getInstance(getApplicationContext());
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

        loadTvshow();

    }

    public void getExtraData(){
        tvShow = getIntent().getParcelableExtra(EXTRA_TV);
        if (tvShow != null) {
            movieId = tvShow.getId();
            judul = tvShow.getOriginal_name();
            tahun = tvShow.getFirst_air_date();
            deskripsi = tvShow.getOverview();
            rating = tvShow.getVoteAverage();
            popularity = tvShow.getPopularity();
            poster = tvShow.getPoster_path();
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
                Toast.makeText(DetailTvActivity.this, "Satu item berhasil dihapus", Toast.LENGTH_SHORT).show();
            } else {
                addFavorite();
            }
            isFavorite = !isFavorite;
            setIconFavorite();
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

    private void loadTvshow() {
        FavoriteMovieHelper movieHelper = FavoriteMovieHelper.getInstance(getApplicationContext());
        movieHelper.Open();
        Cursor cursor = getContentResolver().query(
                Uri.parse(CONTENT_URI_TV + "/" + tvShow.getId()),
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

        contentValues.put(_ID, tvShow.getId());
        contentValues.put(COLUMN_ORIGINAL_NAME_TV, tvShow.getOriginal_name());
        contentValues.put(COLUMN_POSTER_PATH_TV, tvShow.getPoster_path());
        contentValues.put(COLUMN_OVERVIEW_TV, tvShow.getOverview());
        contentValues.put(COLUMN_VOTE_AVERAGE_TV, tvShow.getVoteAverage());
        contentValues.put(COLUMN_RELEASE_DATE_TV, tvShow.getFirst_air_date());
        contentValues.put(COLUMN_POPULARITY_TV, tvShow.getPopularity());

        getContentResolver().insert(CONTENT_URI_TV, contentValues);

        Toast.makeText(this,"Berhasil", Toast.LENGTH_LONG).show();
    }

    private void removeFavorite() {

        getContentResolver().delete(
                Uri.parse(CONTENT_URI_TV + "/" + tvShow.getId()),
                null,
                null
        );
        Toast.makeText(this, "berhasil di hapus", Toast.LENGTH_LONG).show();
    }
}
