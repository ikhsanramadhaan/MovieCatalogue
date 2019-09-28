package com.example.favoritecatalogue;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.favoritecatalogue.model.Film;
import com.squareup.picasso.Picasso;

public class DetailActivityMovie extends AppCompatActivity {

    public static final String EXTRA_FILM ="extra_film" ;
    public static final String POSTER_PATH ="https://image.tmdb.org/t/p/w342/";
    private Film film;
    private String  judul, tahun, deskripsi,poster;
    private Double rating, popularity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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
    }

    public void getExtraData(){
        film = getIntent().getParcelableExtra(EXTRA_FILM);
        if (film != null) {
            judul = film.getTitle();
            tahun = film.getReleaseDate();
            deskripsi = film.getOverview();
            rating = film.getVoteAverage();
            popularity = film.getPopularity();
            poster = film.getPosterPath();
        }
    }
}
