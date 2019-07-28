package com.example.moviecatalogue;


import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private String[] dataName;
    private String[] dataDescription;
    private String[] dataTahun;
    private String[] dataDirector;
    private String[] dataGenre;
    private TypedArray dataPhoto;
    private ListViewMovieAdapter adapter;
    private ArrayList<Film> filmArrayList = new ArrayList<>();
    private RecyclerView listView;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_movie, container, false);

        listView= view.findViewById(R.id.lv_movie);
        adapter = new ListViewMovieAdapter(getContext());

        addItem();
        showRv();

        return view;
    }

    private void addItem() {
        prepare();
        for (int i = 0; i < dataName.length; i++) {
            Film film = new Film();
            film.setPhoto(dataPhoto.getResourceId(i, -1));
            film.setJudul(dataName[i]);
            film.setTahun(dataTahun[i]);
            film.setDeskripsi(dataDescription[i]);
            film.setGenre(dataGenre[i]);
            film.setDirector(dataDirector[i]);
            filmArrayList.add(film);
        }

    }

    private void showRv(){
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setAdapter(adapter);
        listView.setHasFixedSize(true);
        adapter.setFilmArrayList(filmArrayList);

    }

    private void prepare() {
        dataPhoto = getResources().obtainTypedArray(R.array.data_photo_movie);
        dataName = getResources().getStringArray(R.array.data_name_movie);
        dataDescription = getResources().getStringArray(R.array.data_description_movie);
        dataTahun = getResources().getStringArray(R.array.data_year_movie);
        dataGenre = getResources().getStringArray(R.array.data_genre_movie);
        dataDirector = getResources().getStringArray(R.array.data_director_movie);
    }

}
