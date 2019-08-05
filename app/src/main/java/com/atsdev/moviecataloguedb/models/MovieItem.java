package com.atsdev.moviecataloguedb.models;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.Objects;

import static android.provider.BaseColumns._ID;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.ORIGINAL_LANGUAGE;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.ORIGINAL_TITLE;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.OVERVIEW;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.POPULARITY;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.POSTER;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.TITLE;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.VOTE;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.getColumnInt;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.getColumnString;

public class MovieItem implements Parcelable {

    private int id;
    private String poster;
    private String title;
    private String release;
    private String voteAverage;
    private String popularity;
    private String originalLanguage;
    private String originalTitle;
    private String overview;

    public int getId() {
        return id;
    }

    public String getPoster() {
        return poster;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease() {
        return release;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public MovieItem (JSONObject object) {

        try {
            this.id = object.getInt("id");
            String image = object.getString("poster_path");
            this.poster = "https://image.tmdb.org/t/p/w185/" + image;
            this.title = object.getString("title");
            this.release = object.getString("release_date");
            this.voteAverage = object.getString("vote_average");
            this.popularity = object.getString("popularity");
            this.originalLanguage = object.getString("original_language");
            this.originalTitle = object.getString("original_title");
            this.overview = object.getString("overview");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Error Data", Objects.requireNonNull(e.getMessage()));
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.poster);
        dest.writeString(this.title);
        dest.writeString(this.release);
        dest.writeString(this.voteAverage);
        dest.writeString(this.popularity);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.originalTitle);
        dest.writeString(this.overview);
    }

    public MovieItem() {}

    private MovieItem(Parcel in) {
        this.id = in.readInt();
        this.poster = in.readString();
        this.title = in.readString();
        this.release = in.readString();
        this.voteAverage = in.readString();
        this.popularity = in.readString();
        this.originalLanguage = in.readString();
        this.originalTitle = in.readString();
        this.overview = in.readString();
    }

    public static final Creator<MovieItem> CREATOR = new Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel source) {
            return new MovieItem(source);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };

    @NotNull
    @Override
    public String toString() {
        return "MovieItem{" +
                "id = '" + id + "" +
                ",poster_path = '" + poster + "" +
                ",title = '" + title + "" +
                ",release_date = '" + release + "" +
                ",vote_average = '" + voteAverage + "" +
                ",popularity = '" + popularity + "" +
                ",original_language = '" + originalLanguage + "" +
                ",original_title = '" + originalTitle + "" +
                ",overview = '" + overview + "" +
                "}";
    }

    public MovieItem(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.poster = getColumnString(cursor, POSTER);
        this.title = getColumnString(cursor, TITLE);
        this.release = getColumnString(cursor, RELEASE_DATE);
        this.voteAverage = getColumnString(cursor, VOTE);
        this.popularity = getColumnString(cursor, POPULARITY);
        this.originalLanguage = getColumnString(cursor, ORIGINAL_LANGUAGE);
        this.originalTitle = getColumnString(cursor, ORIGINAL_TITLE);
        this.overview = getColumnString(cursor, OVERVIEW);
    }
}
