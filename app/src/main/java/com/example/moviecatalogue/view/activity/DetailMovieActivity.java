package com.example.moviecatalogue.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.dbmovie.movie.FavoriteMovieHelper;
import com.example.moviecatalogue.model.Film;
import com.squareup.picasso.Picasso;

import static com.example.moviecatalogue.base.networks.ApiUrl.POSTER_PATH;

public class DetailMovieActivity extends AppCompatActivity {
    public static final String EXTRA_FILM = "extra_film";
    private FavoriteMovieHelper helper;
    private boolean isFavorite = false;
    private Menu mMenu = null;
    private Film film;
    private int movieId;
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

            if (helper.checkMovie(String.valueOf(movieId))){
                isFavorite = !isFavorite;
            }
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
                    helper.deleteMovie(movieId);
                    Toast.makeText(DetailMovieActivity.this, "Satu item berhasil dihapus", Toast.LENGTH_SHORT).show();
                } else {
                    setAddToFavorite();
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

    private void setAddToFavorite(){
        try {
            if (film != null) {
                long result = helper.insertMovie(film);
                if (result>0){
                    Toast.makeText(this, "Success Add Data ", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "Failed To Add Data", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}


