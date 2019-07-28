package com.example.moviecatalogue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailTvShowActivity extends AppCompatActivity {
    public static final String EXTRA_TV = "extra_tv";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

        setTitle(R.string.detail_tv);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView imageView = findViewById(R.id.photo);
        TextView tvJudul = findViewById(R.id.judul);
        TextView tvTahun = findViewById(R.id.rilis);
        TextView tvGenre = findViewById(R.id.genre);
        TextView tvDirector = findViewById(R.id.director);
        TextView tvDeskripsi = findViewById(R.id.description);

        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TV);

        String judul = tvShow.getJudul();
        String tahun = tvShow.getTahun();
        String genre = tvShow.getGenre();
        String director = tvShow.getDirector();
        String deskripsi = tvShow.getDeskripsi();
        int poster = tvShow.getPhoto();

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
