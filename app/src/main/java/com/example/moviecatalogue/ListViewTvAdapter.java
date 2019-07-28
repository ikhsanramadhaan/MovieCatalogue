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

public class ListViewTvAdapter extends RecyclerView.Adapter<ListViewTvAdapter.ViewHolder> {

    private Context context;
    private ArrayList<TvShow> tvShowArrayList;

    public void setTvShowArrayList (ArrayList<TvShow> tvShowArrayList) {
        this.tvShowArrayList = tvShowArrayList;
        notifyDataSetChanged();

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
        holder.txtjudul.setText(tvShowArrayList.get(position).getJudul());
        holder.txtDescription.setText(tvShowArrayList.get(position).getDeskripsi());
        holder.imgPhoto.setImageResource(tvShowArrayList.get(position).getPhoto());
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
