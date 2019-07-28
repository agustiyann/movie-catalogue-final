package com.atsdev.moviecataloguedb.details;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.atsdev.moviecataloguedb.R;
import com.atsdev.moviecataloguedb.database.TvHelper;
import com.atsdev.moviecataloguedb.models.TvShowItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.BLUR_IMAGE_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.CONTENT_URI_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.KEY_ID_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.ORIGINAL_LANGUAGE_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.ORIGINAL_TITLE_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.OVERVIEW_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.POPULARITY_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.POSTER_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.RELEASE_DATE_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.TITLE_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.VOTE_TV;

public class DetailTvShowActivity extends AppCompatActivity {

    public static final String EXTRA_TVSHOW = "tvshow";
    private Boolean isFavorite = false;

    private TextView like;
    private ImageView btnLike;

    private TvHelper tvHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

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

        TvShowItem item = getIntent().getParcelableExtra(EXTRA_TVSHOW);

        title.setText(item.getName());
        release.setText(item.getFirstAirDate());
        originalTitle.setText(item.getOriginalName());
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
        if (tvHelper != null)
            tvHelper.close();
    }

    private void setFavorite(){
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
        TvShowItem Isi = getIntent().getParcelableExtra("tvshow");
        tvHelper = new TvHelper(this);
        tvHelper.open();

        Cursor cursor = getContentResolver().query(Uri.parse(CONTENT_URI_TV + "/" + Isi.getId()),null,
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
        TvShowItem Isi = getIntent().getParcelableExtra("tvshow");
        ContentValues values = new ContentValues();
        values.put(KEY_ID_TV, Isi.getId());
        values.put(TITLE_TV, Isi.getName());
        values.put(POSTER_TV, Isi.getPoster());
        values.put(BLUR_IMAGE_TV, Isi.getPoster());
        values.put(OVERVIEW_TV, Isi.getOverview());
        values.put(POPULARITY_TV, Isi.getPopularity());
        values.put(ORIGINAL_LANGUAGE_TV, Isi.getOriginalLanguage());
        values.put(ORIGINAL_TITLE_TV, Isi.getOriginalName());
        values.put(VOTE_TV, Isi.getVoteAverage());
        values.put(RELEASE_DATE_TV, Isi.getFirstAirDate());
        getContentResolver().insert(CONTENT_URI_TV, values);
    }

    private void FavoriteRemove(){
        TvShowItem Isi = getIntent().getParcelableExtra("tvshow");
        getContentResolver().delete(Uri.parse(CONTENT_URI_TV + "/" + Isi.getId() ),null,
                null);
    }
}
