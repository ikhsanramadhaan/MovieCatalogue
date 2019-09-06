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
import com.example.moviecatalogue.view.activity.DetailTvActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.moviecatalogue.base.networks.ApiUrl.POSTER_PATH;

public class FavoriteTvAdapter extends RecyclerView.Adapter<FavoriteTvAdapter.ViewHolder> {

    public ArrayList<TvShow> getTvShows() {
        return tvShows;
    }

    private final ArrayList<TvShow> tvShows = new ArrayList<>();
    private Context context;

    public FavoriteTvAdapter(Context context) {
        this.context = context;
    }

    public void setTvShows(ArrayList<TvShow> tvShows) {

        if (tvShows.size() > 0) {
            this.tvShows.clear();
        }
        this.tvShows.addAll(tvShows);
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
        holder.txtjudul.setText(tvShows.get(position).getOriginal_name());
        holder.txtDescription.setText(tvShows.get(position).getOverview());
        Picasso.get().load(POSTER_PATH+ tvShows.get(position).getPoster_path()).into(holder.imgPhoto);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailTvActivity.class);
                intent.putExtra(DetailTvActivity.EXTRA_TV, tvShows.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
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
