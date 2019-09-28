package com.example.moviecatalogue.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.model.Film;
import com.example.moviecatalogue.view.activity.DetailMovieActivity;
import com.squareup.picasso.Picasso;


import static com.example.moviecatalogue.base.networks.ApiUrl.POSTER_PATH;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder> {
    private Context context;
    private Cursor movie_cursor;


    public FavoriteMovieAdapter(Context context) {
        this.context = context;
    }

    public void setMovie_cursor(Cursor movie_cursor) {
        this.movie_cursor = movie_cursor;
    }
    private Film getItem(int position) {
        if (!movie_cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Film(movie_cursor);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Film film = getItem(position);
        holder.txtjudul.setText(film.getTitle());
        holder.txtDescription.setText(film.getOverview());
        Picasso.get().load(POSTER_PATH+ film.getPosterPath()).into(holder.imgPhoto);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_FILM, film);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (movie_cursor == null) return 0;
        return movie_cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtjudul;
        private TextView txtDescription;
        private ImageView imgPhoto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtjudul = itemView.findViewById(R.id.judul);
            txtDescription = itemView.findViewById(R.id.description);
            imgPhoto = itemView.findViewById(R.id.photo);
        }
    }
}
