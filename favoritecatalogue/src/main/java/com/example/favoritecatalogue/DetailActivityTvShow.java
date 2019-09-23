package com.example.favoritecatalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DetailActivityTvShow extends AppCompatActivity {

    public static final String EXTRA_TV ="extra_tv" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);
    }
}
