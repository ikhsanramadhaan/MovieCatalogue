package com.example.moviecatalogue.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.model.TvShow;
import com.squareup.picasso.Picasso;

import static com.example.moviecatalogue.base.networks.ApiUrl.PATH_TV;
import static com.example.moviecatalogue.base.networks.ApiUrl.POSTER_PATH;

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
        TextView tvRating = findViewById(R.id.rating);
        TextView tvPopularity = findViewById(R.id.popularity);
        TextView tvDeskripsi = findViewById(R.id.description);

        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TV);

        String judul = tvShow.getOriginal_name();
        String tahun = tvShow.getFirst_air_date();
        String deskripsi = tvShow.getOverview();
        Double rating = tvShow.getVoteAverage();
        Double popularity = tvShow.getPopularity();
        String poster = tvShow.getPoster_path();

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
        if (item.getItemId() == android.R.id.home){
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
