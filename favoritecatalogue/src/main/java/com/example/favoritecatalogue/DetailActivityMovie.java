package com.example.favoritecatalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DetailActivityMovie extends AppCompatActivity {

    public static final String EXTRA_FILM ="extra_film" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }
}
