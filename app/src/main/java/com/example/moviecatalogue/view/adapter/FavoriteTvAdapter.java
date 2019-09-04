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
import com.example.moviecatalogue.model.TvShow;
import com.example.moviecatalogue.view.activity.DetailMovieActivity;
import com.example.moviecatalogue.view.activity.DetailTvActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.moviecatalogue.base.networks.ApiUrl.POSTER_PATH;

public class FavoriteTvAdapter extends RecyclerView.Adapter<FavoriteTvAdapter.ViewHolder> {

    public ArrayList<TvShow> getMovieList() {
        return movieList;
    }

    private final ArrayList<TvShow> movieList = new ArrayList<>();
    private Context context;

    public FavoriteTvAdapter(Context context) {
        this.context = context;
    }

    public void setMovieList(ArrayList<TvShow> movieList) {

        if (movieList.size() > 0) {
            this.movieList.clear();
        }
        this.movieList.addAll(movieList);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item_tvshow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtjudul.setText(movieList.get(position).getOriginal_name());
        holder.txtDescription.setText(movieList.get(position).getOverview());
        Picasso.get().load(POSTER_PATH+movieList.get(position).getPoster_path()).into(holder.imgPhoto);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailTvActivity.class);
                intent.putExtra(DetailTvActivity.EXTRA_TV, movieList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
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
