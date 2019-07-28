package com.atsdev.moviecataloguedb.details;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.atsdev.moviecataloguedb.R;
import com.atsdev.moviecataloguedb.database.MovieHelper;
import com.atsdev.moviecataloguedb.models.MovieItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.BLUR_IMAGE;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.CONTENT_URI;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.ORIGINAL_LANGUAGE;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.ORIGINAL_TITLE;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.OVERVIEW;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.POPULARITY;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.POSTER;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.TITLE;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.VOTE;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.KEY_ID;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "movie";
    private Boolean isFavorite = false;

    private TextView like;
    private ImageView btnLike;

    private MovieHelper movieHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        ImageView blurImage = findViewById(R.id.blur_image);
        ImageView poster = findViewById(R.id.poster_image);
        TextView title = findViewById(R.id.tv_name);
        TextView release = findViewById(R.id.tv_release);
        TextView originalTitle = findViewById(R.id.tv_original_title);
        TextView originalLanguage = findViewById(R.id.tv_original_language);
        TextView voteAverage = findViewById(R.id.tv_vote_average);
        TextView popularity = findViewById(R.id.tv_popularity);
        TextView overview = findViewById(R.id.tv_description);

        like = findViewById(R.id.tv_like);
        btnLike = findViewById(R.id.btn_like);

        MovieItem item = getIntent().getParcelableExtra(EXTRA_MOVIE);

        title.setText(item.getTitle());
        release.setText(item.getRelease());
        originalTitle.setText(item.getOriginalTitle());
        originalLanguage.setText(item.getOriginalLanguage());
        voteAverage.setText(item.getVoteAverage());
        popularity.setText(item.getPopularity());
        overview.setText(item.getOverview());
        Glide.with(this).load(item.getPoster())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(10, 1)))
                .into(blurImage);
        Glide.with(this).load(item.getPoster())
                .into(poster);

        loadDataSQLite();
        btnLike.setOnClickListener((v) -> {
            if (isFavorite)
                FavoriteRemove();
            else
                FavoriteSave();

            isFavorite = !isFavorite;
            setFavorite();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (movieHelper != null)
            movieHelper.close();
    }

    public void setFavorite(){
        if (isFavorite){
            btnLike.setImageResource(R.drawable.ic_favorite_black_24dp);
            like.setText(getString(R.string.liked));
        }
        else {
            btnLike.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            like.setText(getString(R.string.like));
        }
    }

    private void loadDataSQLite(){
        MovieItem Isi = getIntent().getParcelableExtra("movie");
        movieHelper = new MovieHelper(this);
        movieHelper.open();

        Cursor cursor = getContentResolver().query(Uri.parse(CONTENT_URI + "/" + Isi.getId()),null,
                null,
                null,
                null);

        if (cursor != null){
            if (cursor.moveToFirst())isFavorite=true;
            cursor.close();
        }
        setFavorite();
    }

    private void FavoriteSave(){
        MovieItem Isi = getIntent().getParcelableExtra("movie");
        ContentValues values = new ContentValues();
        values.put(KEY_ID,Isi.getId());
        values.put(TITLE,Isi.getTitle());
        values.put(POSTER,Isi.getPoster());
        values.put(BLUR_IMAGE,Isi.getPoster());
        values.put(OVERVIEW,Isi.getOverview());
        values.put(POSTER,Isi.getPoster());
        values.put(POPULARITY,Isi.getPopularity());
        values.put(ORIGINAL_LANGUAGE,Isi.getOriginalLanguage());
        values.put(ORIGINAL_TITLE,Isi.getOriginalTitle());
        values.put(VOTE,Isi.getVoteAverage());
        values.put(RELEASE_DATE,Isi.getRelease());
        getContentResolver().insert(CONTENT_URI,values);
        Toast.makeText(this,"Film has Set Favorited",Toast.LENGTH_SHORT).show();
    }

    private void FavoriteRemove(){
        MovieItem Isi = getIntent().getParcelableExtra("movie");
        getContentResolver().delete(Uri.parse(CONTENT_URI + "/" + Isi.getId() ),null,
                null);
        Toast.makeText(this,"Unfavorited",Toast.LENGTH_SHORT).show();
    }
}
