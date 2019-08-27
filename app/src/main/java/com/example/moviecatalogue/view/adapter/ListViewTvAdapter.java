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

import com.example.moviecatalogue.view.DetailTvShowActivity;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.model.TvShow;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.moviecatalogue.base.networks.ApiUrl.BASE_URL;
import static com.example.moviecatalogue.base.networks.ApiUrl.PATH_TV;
import static com.example.moviecatalogue.base.networks.ApiUrl.POSTER_PATH;

public class ListViewTvAdapter extends RecyclerView.Adapter<ListViewTvAdapter.ViewHolder> {

    private Context context;
    private List<TvShow> tvShowArrayList = new ArrayList<>();



    public void setTvShowArrayList (List<TvShow> tvShowArrayList) {
        this.tvShowArrayList = tvShowArrayList;
        notifyDataSetChanged();

    }

    public List<TvShow> getTvShowArrayList() {
        return tvShowArrayList;
    }

    public ListViewTvAdapter(Context context) {
        this.context = context;
        tvShowArrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item_tvshow, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtjudul.setText(tvShowArrayList.get(position).getOriginal_name());
        holder.txtDescription.setText(tvShowArrayList.get(position).getOverview());
        Picasso.get().load(POSTER_PATH+tvShowArrayList.get(position).getPoster_path()).into(holder.imgPhoto);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailTvShowActivity.class);
                intent.putExtra(DetailTvShowActivity.EXTRA_TV, tvShowArrayList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvShowArrayList.size();
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

//
    }
}
