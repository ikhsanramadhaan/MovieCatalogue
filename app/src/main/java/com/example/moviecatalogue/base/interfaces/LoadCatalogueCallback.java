package com.example.moviecatalogue.base.interfaces;

import android.database.Cursor;

import com.example.moviecatalogue.model.Film;

import java.util.ArrayList;

public interface LoadCatalogueCallback {
    void preExcute();

    void postExcute(ArrayList<Film> films);
}
