package com.example.moviecatalogue;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListViewMovieAdapter extends RecyclerView.Adapter<ListViewMovieAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Film> filmArrayList;

    public void setFilmArrayList(ArrayList<Film> filmArrayList) {
        this.filmArrayList = filmArrayList;
        notifyDataSetChanged();
    }


    public ListViewMovieAdapter(Context context) {
        this.context = context;
        filmArrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtjudul.setText(filmArrayList.get(position).getJudul());
        holder.txtDescription.setText(filmArrayList.get(position).getDeskripsi());
        holder.imgPhoto.setImageResource(filmArrayList.get(position).getPhoto());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_FILM, filmArrayList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filmArrayList.size();
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
