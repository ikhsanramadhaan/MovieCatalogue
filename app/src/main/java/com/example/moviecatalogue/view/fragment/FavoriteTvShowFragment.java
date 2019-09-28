package com.example.moviecatalogue.view.fragment;


import android.app.ProgressDialog;
import android.database.Cursor;
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

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.dbmovie.movie.FavoriteTvHelper;
import com.example.moviecatalogue.model.TvShow;
import com.example.moviecatalogue.view.adapter.FavoriteTvAdapter;

import java.util.ArrayList;

import static com.example.moviecatalogue.dbmovie.movie.DbContract.CONTENT_URI_TV;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvShowFragment extends Fragment {
    private RecyclerView listView;
    private FavoriteTvAdapter adapter;
    private ProgressBar progressBar;
    private ProgressDialog dialog;
    private FavoriteTvHelper helper;
    private TextView textViewEmpty;
    private TvShow tvShow;
    private Cursor list_tv;
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

        new LoadTvAsync().execute();
        showRecyclerMovie();
    }
    private void showRecyclerMovie() {
        adapter = new FavoriteTvAdapter(getContext());
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
        listView.addItemDecoration(itemDecoration);
        adapter.setCursor(list_tv);
        listView.setAdapter(adapter);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST_STATE_KEY, tvShows);
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadTvAsync().execute();
        showRecyclerMovie();

    }

    private class LoadTvAsync extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();

        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver()
                    .query(
                            CONTENT_URI_TV,
                            null,
                            null,
                            null,
                            null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            dialog.dismiss();
            list_tv = cursor;
            adapter.setCursor(list_tv);
            adapter.notifyDataSetChanged();

        }
    }
}
