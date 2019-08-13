package com.example.moviecatalogue.view;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.base.api.ApiInterface;
import com.example.moviecatalogue.base.networks.ApiClient;
import com.example.moviecatalogue.model.Film;
import com.example.moviecatalogue.model.MovieResult;
import com.example.moviecatalogue.view.adapter.ListViewMovieAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.moviecatalogue.base.networks.ApiUrl.API_KEY;
import static com.example.moviecatalogue.base.networks.ApiUrl.LANGUAGE_ENGLISH;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {


    private ArrayList<Film> filmArrayList = new ArrayList<>();
    private ProgressDialog dialog;
    private RecyclerView listView;
    private ListViewMovieAdapter adapter;
    final public static String KEY_MOVIE = "key_movie";

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_movie, container, false);


        listView = view.findViewById(R.id.lv_movie);
        adapter = new ListViewMovieAdapter(getContext());
        dialog = new ProgressDialog(getContext());
        dialog.setMessage(getString(R.string.message));
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setHasFixedSize(true);
        listView.setAdapter(adapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        listView.addItemDecoration(itemDecoration);

        dialog.show();
        response();

        if (savedInstanceState != null){
            filmArrayList = savedInstanceState.getParcelableArrayList(KEY_MOVIE);
        }


        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(KEY_MOVIE, new ArrayList<>(adapter.getFilmArrayList()));
        super.onSaveInstanceState(outState);
    }

    public void response(){
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<MovieResult> call = apiInterface.getMovie(API_KEY);
        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                if (response.isSuccessful()){
                    dialog.dismiss();
                    adapter.setFilmArrayList(response.body().getResultsMovie());
                }else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }
}
