package com.example.moviecatalogue.view.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.base.api.ApiInterface;
import com.example.moviecatalogue.base.networks.ApiClient;
import com.example.moviecatalogue.model.Film;
import com.example.moviecatalogue.model.MovieResult;
import com.example.moviecatalogue.view.adapter.ListViewMovieAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.moviecatalogue.base.networks.ApiUrl.API_KEY;
import static com.example.moviecatalogue.base.networks.ApiUrl.LANGUAGE_ENGLISH;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {


    private List<Film> filmArrayList = new ArrayList<>();
    private ProgressDialog dialog;
    private RecyclerView listView;
    private ListViewMovieAdapter adapter;


    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.lv_movie);
        dialog = new ProgressDialog(getContext());
        dialog.setMessage(getString(R.string.message));
        adapter = new ListViewMovieAdapter(getContext());
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setHasFixedSize(true);

        if (savedInstanceState != null){
            ArrayList<Film> films;
            films = savedInstanceState.getParcelableArrayList("movie");
            adapter.setFilmArrayList(films);
            listView.setAdapter(adapter);
        }else {
            response();
        }


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                responseSearch(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                responseSearch(s);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("movie", new ArrayList<>(adapter.getFilmArrayList()));
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null){
            ArrayList<Film> films;
            films = savedInstanceState.getParcelableArrayList("movie");
            adapter.setFilmArrayList(films);
            listView.setAdapter(adapter);
        }
    }

    private void response(){
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<MovieResult> call = apiInterface.getMovie(API_KEY);
        dialog.show();
        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                if (response.isSuccessful()){
                    filmArrayList = response.body().getResultsMovie();
                    dialog.dismiss();
                    adapter.setFilmArrayList(filmArrayList);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    private void responseSearch(String query){
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<MovieResult> call = apiInterface.getSeachMovie(API_KEY, LANGUAGE_ENGLISH, query);
        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                if (response.isSuccessful()){
                    filmArrayList = response.body().getResultsMovie();
                    adapter.setFilmArrayList(filmArrayList);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

            }
        });

    }
}
