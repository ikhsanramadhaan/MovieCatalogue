package com.example.moviecatalogue.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.view.fragment.FavoriteFragment;
import com.example.moviecatalogue.view.fragment.MovieFragment;
import com.example.moviecatalogue.view.fragment.TvshowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;
    private String title = "";
    private Fragment fragment = new Fragment();
    private MovieFragment movieFragment ;
    private TvshowFragment tvshowFragment;
    int navtab = 1;

    public static final String KEY_TITLE = "title";
    public static final String KEY_FRAGMENT = "fragment";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_moview:
                        fragment = new MovieFragment();
                        title= getString(R.string.title_movies);
                        break;
                    case R.id.navigation_tvshows:
                        fragment = new TvshowFragment();
                        title = getString(R.string.title_tvshows);
                        break;
                    case R.id.navigation_favorite:
                        fragment = new FavoriteFragment();
                        title =getString(R.string.title_fav);
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
                setTitle(title);

                return true;
            }
        });

        if (savedInstanceState == null){
            bottomNavigationView.setSelectedItemId(R.id.navigation_moview);
            setTitle(title);
        }else {
            fragment = getSupportFragmentManager().getFragment(savedInstanceState, KEY_FRAGMENT);
            title = savedInstanceState.getString(KEY_TITLE);

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
            setTitle(title);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_change_settings){
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_TITLE, title);
        getSupportFragmentManager().putFragment(outState, KEY_FRAGMENT, fragment);
        super.onSaveInstanceState(outState);
    }
}
