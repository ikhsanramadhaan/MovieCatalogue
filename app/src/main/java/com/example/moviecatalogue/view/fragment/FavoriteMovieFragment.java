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

import com.example.moviecatalogue.base.interfaces.LoadCatalogueCallback;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.dbmovie.movie.FavoriteMovieHelper;
import com.example.moviecatalogue.model.Film;
import com.example.moviecatalogue.view.adapter.FavoriteMovieAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment implements LoadCatalogueCallback {
    private RecyclerView listView;
    private FavoriteMovieAdapter adapter;
    private ProgressBar progressBar;
    private ProgressDialog dialog;
    private FavoriteMovieHelper helper;
    private TextView textViewEmpty;
    private final static String LIST_STATE_KEY = "STATE";

    private ArrayList<Film> films = new ArrayList<>();
    public FavoriteMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.movie_list);
        progressBar = view.findViewById(R.id.favProgressBar);
        textViewEmpty = view.findViewById(R.id.textFav);
        dialog = new ProgressDialog(getContext());
        dialog.setMessage(getString(R.string.message));

        adapter = new FavoriteMovieAdapter(getContext());
        helper = FavoriteMovieHelper.getInstance(getActivity());
        helper.Open();

        progressBar.setVisibility(View.INVISIBLE);
        textViewEmpty.setVisibility(View.INVISIBLE);

        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);

        listView.addItemDecoration(itemDecoration);

        if (savedInstanceState == null){
            new LoadFilmAsync(helper, this).execute();
        }else {
            final ArrayList<Film> arrayList =savedInstanceState.getParcelableArrayList(LIST_STATE_KEY);
            assert arrayList != null;
            films.addAll(arrayList);
            adapter.setFilms(arrayList);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST_STATE_KEY, films);
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadFilmAsync(helper, this).execute();
    }

    @Override
    public void preExcute() {
        dialog.show();
    }

    @Override
    public void postExcute(ArrayList<Film> filmArrayList) {
        dialog.dismiss();
        adapter.setFilms(filmArrayList);
        listView.setAdapter(adapter);
        films.addAll(filmArrayList);
    }

    private static class LoadFilmAsync extends AsyncTask<Void, Void, ArrayList<Film>> {
        private WeakReference<FavoriteMovieHelper> favoriteMovieHelperWeakReference;
        private WeakReference<LoadCatalogueCallback> weakCallback;

        public LoadFilmAsync(FavoriteMovieHelper movieHelperWeakReference, LoadCatalogueCallback callback) {
            this.favoriteMovieHelperWeakReference = new WeakReference<>(movieHelperWeakReference);
            this.weakCallback = new WeakReference<>(callback);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExcute();

        }

        @Override
        protected ArrayList<Film> doInBackground(Void... voids) {
            return favoriteMovieHelperWeakReference.get().getAllMovie();
        }

        @Override
        protected void onPostExecute(ArrayList<Film> notes) {
            super.onPostExecute(notes);
            weakCallback.get().postExcute(notes);

        }
    }
}
