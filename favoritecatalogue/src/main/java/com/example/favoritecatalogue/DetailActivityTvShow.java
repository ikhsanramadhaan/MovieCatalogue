package com.example.favoritecatalogue;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.favoritecatalogue.model.TvShow;
import com.squareup.picasso.Picasso;

public class DetailActivityTvShow extends AppCompatActivity {

    public static final String EXTRA_TV ="extra_tv" ;
    public static final String POSTER_PATH ="https://image.tmdb.org/t/p/w342/";
    private TvShow tvShow;
    private String  judul, tahun, deskripsi,poster;
    private Double rating, popularity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);
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
        tvShow = getIntent().getParcelableExtra(EXTRA_TV);
        if (tvShow != null) {
            judul = tvShow.getOriginal_name();
            tahun = tvShow.getFirst_air_date();
            deskripsi = tvShow.getOverview();
            rating = tvShow.getVoteAverage();
            popularity = tvShow.getPopularity();
            poster = tvShow.getPoster_path();
        }
    }
}
