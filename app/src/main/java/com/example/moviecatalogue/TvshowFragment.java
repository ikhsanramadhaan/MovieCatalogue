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
public class TvshowFragment extends Fragment {

    private String[] dataName;
    private String[] dataDescription;
    private String[] dataTahun;
    private String[] dataDirector;
    private String[] dataGenre;
    private TypedArray dataPhoto;
    private ListViewTvAdapter adapter;
    private ArrayList<TvShow> tvShowArrayList ;
    public TvshowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_tvshow, container, false);
        adapter = new ListViewTvAdapter(getContext());
        RecyclerView listView = view.findViewById(R.id.lv_tvshow);
        listView.setHasFixedSize(true);

        prepare();
        addItem();

        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setAdapter(adapter);

        return view;
    }

    private void addItem() {
        tvShowArrayList = new ArrayList<>();
        for (int i = 0; i < dataName.length; i++) {
            TvShow tvShow = new TvShow();
            tvShow.setPhoto(dataPhoto.getResourceId(i, -1));
            tvShow.setJudul(dataName[i]);
            tvShow.setTahun(dataTahun[i]);
            tvShow.setDeskripsi(dataDescription[i]);
            tvShow.setGenre(dataGenre[i]);
            tvShow.setDirector(dataDirector[i]);
            tvShowArrayList.add(tvShow);
        }
        adapter.setTvShowArrayList(tvShowArrayList);
    }

    private void prepare() {
        dataPhoto = getResources().obtainTypedArray(R.array.data_photo_tvshow);
        dataName = getResources().getStringArray(R.array.data_name_tvshow);
        dataDescription = getResources().getStringArray(R.array.data_description_tvshow);
        dataTahun = getResources().getStringArray(R.array.data_year_tvshow);
        dataGenre = getResources().getStringArray(R.array.data_genre_tvshow);
        dataDirector = getResources().getStringArray(R.array.data_director_tvshow);
    }

}
