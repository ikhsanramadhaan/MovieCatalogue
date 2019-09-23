package com.example.favoritecatalogue;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private String title = "";
    private Fragment fragment = new Fragment();
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
                        fragment = new FavoriteMovieFragment();
                        title= getString(R.string.title_movies);
                        break;
                    case R.id.navigation_tvshows:
                        fragment = new FavoriteTvShowFragment();
                        title = getString(R.string.title_tvshows);
                        break;
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
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_TITLE, title);
        getSupportFragmentManager().putFragment(outState, KEY_FRAGMENT, fragment);
        super.onSaveInstanceState(outState);
    }
}
