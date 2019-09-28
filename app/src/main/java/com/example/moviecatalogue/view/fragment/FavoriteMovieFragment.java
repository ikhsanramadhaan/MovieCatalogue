package com.example.moviecatalogue.view.fragment;


import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.dbmovie.movie.FavoriteMovieHelper;
import com.example.moviecatalogue.view.adapter.FavoriteMovieAdapter;

import static com.example.moviecatalogue.dbmovie.movie.DbContract.CONTENT_URI_MOVIE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment{
    private RecyclerView listView;
    private FavoriteMovieAdapter adapter;
    private ProgressBar progressBar;
    private ProgressDialog dialog;
    private FavoriteMovieHelper helper;
    private TextView textViewEmpty;
    private final static String LIST_STATE_KEY = "STATE";
    private Cursor list_movie;

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


        helper = FavoriteMovieHelper.getInstance(getActivity());
        helper.Open();

        progressBar.setVisibility(View.INVISIBLE);
        textViewEmpty.setVisibility(View.INVISIBLE);

        new LoadFilmAsync().execute();
        showRecyclerMovie();
    }

    private void showRecyclerMovie() {
        adapter = new FavoriteMovieAdapter(getContext());
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
        listView.addItemDecoration(itemDecoration);
        adapter.setMovie_cursor(list_movie);
        listView.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadFilmAsync().execute();
        showRecyclerMovie();

    }


    private class LoadFilmAsync extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();

        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver()
                    .query(
                            CONTENT_URI_MOVIE,
                            null,
                            null,
                            null,
                            null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            dialog.dismiss();
            list_movie = cursor;
            adapter.setMovie_cursor(list_movie);
            adapter.notifyDataSetChanged();

        }
    }
}
