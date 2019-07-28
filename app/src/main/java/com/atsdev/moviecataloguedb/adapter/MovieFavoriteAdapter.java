package com.atsdev.moviecataloguedb.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atsdev.moviecataloguedb.R;
import com.atsdev.moviecataloguedb.details.DetailMovieActivity;
import com.atsdev.moviecataloguedb.models.MovieItem;
import com.bumptech.glide.Glide;

public class MovieFavoriteAdapter extends RecyclerView.Adapter<MovieFavoriteAdapter.CardViewViewHolder> {

    private Context context;
    private Cursor cursor;

    public MovieFavoriteAdapter(Context context) {
        this.context = context;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, null, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);

        return new CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewViewHolder cardViewViewHolder, int i) {

        MovieItem movieItem = getItem(i);

        cardViewViewHolder.tvName.setText(movieItem.getTitle());
        cardViewViewHolder.tvRelease.setText(movieItem.getRelease());
        cardViewViewHolder.tvDescription.setText(movieItem.getOverview());
        Glide.with(context).load(movieItem.getPoster())
                .into(cardViewViewHolder.imgMovie);

        cardViewViewHolder.itemView.setOnClickListener((v) -> {
            Intent intent = new Intent(context, DetailMovieActivity.class);
            intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieItem);
            context.startActivity(intent);
        });

        Log.d("TITLE", ""+movieItem.getTitle());

    }

    private MovieItem getItem(int i) {
        if (!cursor.moveToPosition(i)) {
            throw new IllegalStateException("Position invalid");
        }
        return new MovieItem(cursor);
    }

    @Override
    public int getItemCount() {
        if (cursor == null)
            return 0;
        return cursor.getCount();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {

        final ImageView imgMovie;
        final TextView tvName;
        final TextView tvRelease;
        final TextView tvDescription;

        CardViewViewHolder(@NonNull View itemView) {
            super(itemView);

            imgMovie = itemView.findViewById(R.id.img_item_photo);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvRelease = itemView.findViewById(R.id.tv_item_release);
            tvDescription = itemView.findViewById(R.id.tv_description);
        }
    }
}
