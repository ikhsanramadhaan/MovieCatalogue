package com.example.moviecatalogue.view.fragment;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.moviecatalogue.base.interfaces.LoadCallbackTv;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.dbmovie.movie.FavoriteTvHelper;
import com.example.moviecatalogue.model.TvShow;
import com.example.moviecatalogue.view.adapter.FavoriteTvAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvShowFragment extends Fragment implements LoadCallbackTv {
    private RecyclerView listView;
    private FavoriteTvAdapter adapter;
    private ProgressBar progressBar;
    private ProgressDialog dialog;
    private FavoriteTvHelper helper;
    private TextView textViewEmpty;
    private static final String LIST_STATE_KEY = "key";

    private ArrayList<TvShow> tvShows = new ArrayList<>();

    public FavoriteTvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.movie_list);
        progressBar = view.findViewById(R.id.favProgressBar);
        textViewEmpty = view.findViewById(R.id.textFav);
        dialog = new ProgressDialog(getContext());
        dialog.setMessage(getString(R.string.message));

        adapter = new FavoriteTvAdapter(getContext());
        helper = FavoriteTvHelper.getInstance(getActivity());
        helper.Open();

        progressBar.setVisibility(View.INVISIBLE);
        textViewEmpty.setVisibility(View.INVISIBLE);

        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);

        listView.addItemDecoration(itemDecoration);

        if (savedInstanceState == null){
            new LoadTvAsync(helper, this).execute();
        }else {
            final ArrayList<TvShow> arrayList = savedInstanceState.getParcelableArrayList(LIST_STATE_KEY);
            assert arrayList != null;
            tvShows.addAll(arrayList);
            adapter.setTvShows(arrayList);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST_STATE_KEY, tvShows);
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadTvAsync(helper, this).execute();
    }

    @Override
    public void preExcute() {
        dialog.show();
    }

    @Override
    public void postExcute(ArrayList<TvShow> filmArrayList) {
        dialog.dismiss();
        adapter.setTvShows(filmArrayList);
        listView.setAdapter(adapter);
        tvShows.addAll(filmArrayList);
    }

    private static class LoadTvAsync extends AsyncTask<Void, Void, ArrayList<TvShow>> {
        private WeakReference<FavoriteTvHelper> favoriteMovieHelperWeakReference;
        private WeakReference<LoadCallbackTv> weakCallback;

        public LoadTvAsync(FavoriteTvHelper movieHelperWeakReference, LoadCallbackTv callback) {
            this.favoriteMovieHelperWeakReference = new WeakReference<>(movieHelperWeakReference);
            this.weakCallback = new WeakReference<>(callback);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExcute();

        }

        @Override
        protected ArrayList<TvShow> doInBackground(Void... voids) {
            return favoriteMovieHelperWeakReference.get().getAllTv();
        }

        @Override
        protected void onPostExecute(ArrayList<TvShow> notes) {
            super.onPostExecute(notes);
            weakCallback.get().postExcute(notes);

        }
    }
}
