package com.example.moviecatalogue.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.base.api.ApiInterface;
import com.example.moviecatalogue.view.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MenuItem prevMenu = null;
    private ViewPager mViewPager;
    private BottomNavigationView bottomNavigationView;
    private String title = "";
    public static final String TITLE ="title";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = getString(R.string.title_movies);
        setTitle(title);

        mViewPager = findViewById(R.id.viewpager);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_moview:
                    mViewPager.setCurrentItem(0);
                    title = getString(R.string.title_movies);
                    setTitle(title);
                    break;
                    case R.id.navigation_tvshows:
                        mViewPager.setCurrentItem(1);
                        title = getString(R.string.title_tvshows);
                        setTitle(title);
                        break;
                }
                return false;
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenu != null)
                    prevMenu.setChecked(false);
                else
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);

                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenu = bottomNavigationView.getMenu().getItem(position);

                switch (position){
                    case 0:
                        setTitle(getString(R.string.title_movies));
                        break;
                    case 1:
                        setTitle(getString(R.string.title_tvshows));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (savedInstanceState != null){
            title = savedInstanceState.getString(TITLE);
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
        outState.putString(TITLE, title);
        super.onSaveInstanceState(outState);
    }
}
