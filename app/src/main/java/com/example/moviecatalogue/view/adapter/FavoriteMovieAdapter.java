package com.example.moviecatalogue.view.adapter;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

import static com.example.moviecatalogue.base.networks.ApiUrl.POSTER_PATH;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Film> films = new ArrayList<>();


    public FavoriteMovieAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Film> getFilms() {

        return films;
    }

    public void setFilms(ArrayList<Film> tvShowArrayList) {
        if (films.size() > 0){
            films.clear();
        }

        this.films.addAll(tvShowArrayList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtjudul.setText(films.get(position).getTitle());
        holder.txtDescription.setText(films.get(position).getOverview());
        Picasso.get().load(POSTER_PATH+ films.get(position).getPosterPath()).into(holder.imgPhoto);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_FILM, films.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return films.size();
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
