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
import com.example.favoritecatalogue.DetailActivityTvShow;
import com.example.favoritecatalogue.R;
import com.example.favoritecatalogue.model.Film;
import com.example.favoritecatalogue.model.TvShow;
import com.squareup.picasso.Picasso;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.ViewHolder> {
    private Cursor cursor;
    private Context context;

    public TvAdapter(Context context) {
        this.context = context;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    private TvShow getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new TvShow(cursor);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TvShow tvShow = getItem(position);
        holder.txtjudul.setText(tvShow.getOriginal_name());
        holder.txtDescription.setText(tvShow.getOverview());
        Picasso.get().load( tvShow.getPoster_path()).into(holder.imgPhoto);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivityTvShow.class);
                intent.putExtra(DetailActivityTvShow.EXTRA_TV, tvShow);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;
        return cursor.getCount();
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
