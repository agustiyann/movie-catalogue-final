package com.atsdev.moviefavoriteapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atsdev.moviefavoriteapp.R;
import com.atsdev.moviefavoriteapp.entity.MovieItem;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private final ArrayList<MovieItem> mData = new ArrayList<>();

    private ArrayList<MovieItem> getmData() {
        return mData;
    }

    public void setMovieData(ArrayList<MovieItem> itemData) {
        mData.clear();
        mData.addAll(itemData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return getmData().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
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

            Log.e("TITLE", "" + movieItem.getTitle());
        }
    }
}
