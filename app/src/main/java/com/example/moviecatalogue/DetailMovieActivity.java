package com.example.moviecatalogue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

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
        TextView tvGenre = findViewById(R.id.genre);
        TextView tvDirector = findViewById(R.id.director);
        TextView tvDeskripsi = findViewById(R.id.description);

        Film film = getIntent().getParcelableExtra(EXTRA_FILM);

        String judul = film.getJudul();
        String tahun = film.getTahun();
        String genre = film.getGenre();
        String director = film.getDirector();
        String deskripsi = film.getDeskripsi();
        int poster = film.getPhoto();

        imageView.setImageResource(poster);
        tvJudul.setText(judul);
        tvTahun.setText(tahun);
        tvGenre.setText(genre);
        tvDeskripsi.setText(deskripsi);
        tvDirector.setText(director);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
