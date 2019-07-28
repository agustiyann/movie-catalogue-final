package com.atsdev.moviecataloguedb.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atsdev.moviecataloguedb.R;
import com.atsdev.moviecataloguedb.models.MovieItem;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private final ArrayList<MovieItem> mData = new ArrayList<>();

    public void setMovieData(ArrayList<MovieItem> itemData) {
        mData.clear();
        mData.addAll(itemData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(mData.get(i));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imgMovie;
        final TextView tvName;
        final TextView tvRelease;
        final TextView tvDescription;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMovie = itemView.findViewById(R.id.img_item_photo);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvRelease = itemView.findViewById(R.id.tv_item_release);
            tvDescription = itemView.findViewById(R.id.tv_description);
        }

        void bind(MovieItem movieItem) {
            tvName.setText(movieItem.getTitle());
            tvRelease.setText(movieItem.getRelease());
            tvDescription.setText(movieItem.getOverview());
            Glide.with(itemView).load(movieItem.getPoster())
                    .into(imgMovie);
        }
    }
}
