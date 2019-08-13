package com.example.moviecatalogue.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.model.Film;
import com.squareup.picasso.Picasso;

import static com.example.moviecatalogue.base.networks.ApiUrl.PATH_MOVIE;
import static com.example.moviecatalogue.base.networks.ApiUrl.PATH_TV;
import static com.example.moviecatalogue.base.networks.ApiUrl.POSTER_PATH;

public class DetailMovieActivity extends AppCompatActivity {
    public static final String EXTRA_FILM = "extra_film";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        setTitle(R.string.detail_movie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView imageView = findViewById(R.id.photo);
        TextView tvJudul = findViewById(R.id.judul);
        TextView tvTahun = findViewById(R.id.rilis);
        TextView tvRating = findViewById(R.id.rating);
        TextView tvPopularity = findViewById(R.id.popularity);
        TextView tvDeskripsi = findViewById(R.id.description);

        Film film = getIntent().getParcelableExtra(EXTRA_FILM);

        String judul = film.getTitle();
        String tahun = film.getReleaseDate();
        String deskripsi = film.getOverview();
        Double rating = film.getVoteAverage();
        Double popularity = film.getPopularity();
        String poster = film.getPosterPath();

        Picasso.get().load(POSTER_PATH+poster).into(imageView);
        tvJudul.setText(judul);
        tvTahun.setText(tahun);
        tvRating.setText(String.valueOf(rating));
        tvPopularity.setText(String.valueOf(popularity));
        tvDeskripsi.setText(deskripsi);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
