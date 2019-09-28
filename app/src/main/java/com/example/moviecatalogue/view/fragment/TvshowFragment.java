package com.example.moviecatalogue.view.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.base.api.ApiInterface;
import com.example.moviecatalogue.base.networks.ApiClient;
import com.example.moviecatalogue.model.TvResult;
import com.example.moviecatalogue.model.TvShow;
import com.example.moviecatalogue.view.adapter.ListViewTvAdapter;

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
public class TvshowFragment extends Fragment {

    private ListViewTvAdapter adapter;
    private ProgressDialog progressDialog;
    private RecyclerView listView;
    private List<TvShow> tvShowArrayList = new ArrayList<>() ;

    public TvshowFragment() {
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




        return inflater.inflate(R.layout.fragment_tvshow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ListViewTvAdapter(getContext());
        listView = view.findViewById(R.id.lv_tvshow);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.message));

        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setAdapter(adapter);

        if (savedInstanceState != null){
            ArrayList<TvShow> tvShows;
            tvShows = savedInstanceState.getParcelableArrayList("tv");
            adapter.setTvShowArrayList(tvShows);
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
        outState.putParcelableArrayList("tv", new ArrayList<>(adapter.getTvShowArrayList()));
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            response();
        }else {
            ArrayList<TvShow> tvShows;
            tvShows = savedInstanceState.getParcelableArrayList("tv");
            adapter.setTvShowArrayList(tvShows);
            adapter.notifyDataSetChanged();
        }
    }

    public void response(){
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TvResult> tvResultCall = apiInterface.getTvShow(API_KEY);
        progressDialog.show();
        tvResultCall.enqueue(new Callback<TvResult>() {
            @Override
            public void onResponse(Call<TvResult> call, Response<TvResult> response) {
                if (response.isSuccessful()) {
                    tvShowArrayList = response.body().getGetTvShow();
                    progressDialog.dismiss();
                    adapter.setTvShowArrayList(tvShowArrayList);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<TvResult> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "data not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void responseSearch(String query){
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TvResult> call = apiInterface.getSeachTv(API_KEY, LANGUAGE_ENGLISH, query);
//        progressDialog.show();
        call.enqueue(new Callback<TvResult>() {
            @Override
            public void onResponse(Call<TvResult> call, Response<TvResult> response) {
                if (response.isSuccessful()){
                    tvShowArrayList = response.body().getGetTvShow();
//                    progressDialog.dismiss();
                    adapter.setTvShowArrayList(tvShowArrayList);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<TvResult> call, Throwable t) {
            }
        });

    }

}
