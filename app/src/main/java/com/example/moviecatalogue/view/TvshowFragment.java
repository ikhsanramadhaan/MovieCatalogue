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
import android.widget.Toast;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.base.api.ApiInterface;
import com.example.moviecatalogue.base.networks.ApiClient;
import com.example.moviecatalogue.model.TvResult;
import com.example.moviecatalogue.model.TvShow;
import com.example.moviecatalogue.view.adapter.ListViewTvAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.moviecatalogue.base.networks.ApiUrl.API_KEY;
import static com.example.moviecatalogue.base.networks.ApiUrl.LANGUAGE_ENGLISH;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvshowFragment extends Fragment  {

    private ListViewTvAdapter adapter;
    private ProgressDialog progressDialog;

    private RecyclerView listView;
    private ArrayList<TvShow> tvShowArrayList = new ArrayList<>() ;
    final public static String KEY_TV = "key_tv";

    public TvshowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_tvshow, container, false);

        adapter = new ListViewTvAdapter(getContext());
        listView = view.findViewById(R.id.lv_tvshow);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.message));

        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setAdapter(adapter);

        adapter = new ListViewTvAdapter(getContext());
        adapter.setTvShowArrayList(tvShowArrayList);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setAdapter(adapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        listView.addItemDecoration(itemDecoration);
        response();
        if (savedInstanceState!=null) {
            tvShowArrayList = savedInstanceState.getParcelableArrayList(KEY_TV);
            adapter.setTvShowArrayList(tvShowArrayList);
            listView.setAdapter(adapter);
        }

        return view;
    }

    public void response(){
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TvResult> tvResultCall = apiInterface.getTvShow(API_KEY);
        progressDialog.show();
        tvResultCall.enqueue(new Callback<TvResult>() {
            @Override
            public void onResponse(Call<TvResult> call, Response<TvResult> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    adapter.setTvShowArrayList(response.body().getGetTvShow());
                }else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<TvResult> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_TV, new ArrayList<>(adapter.getTvShowArrayList()));
    }


}
