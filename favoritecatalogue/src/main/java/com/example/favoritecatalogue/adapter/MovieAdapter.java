package com.example.favoritecatalogue.adapter;

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

import com.example.favoritecatalogue.DetailActivityMovie;
import com.example.favoritecatalogue.R;
import com.example.favoritecatalogue.model.Film;
import com.squareup.picasso.Picasso;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private Context context;
    private Cursor movie_cursor;
    public static final String POSTER_PATH ="https://image.tmdb.org/t/p/w342/";

    public MovieAdapter(Context context) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Film film = getItem(position);
        holder.txtjudul.setText(film.getTitle());
        holder.txtDescription.setText(film.getOverview());
        Picasso.get().load(POSTER_PATH+film.getPosterPath()).into(holder.imgPhoto);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivityMovie.class);
                intent.putExtra(DetailActivityMovie.EXTRA_FILM, film);
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
