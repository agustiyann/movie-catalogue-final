package com.atsdev.moviecataloguedb.models;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import static android.provider.BaseColumns._ID;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.ORIGINAL_LANGUAGE_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.ORIGINAL_TITLE_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.OVERVIEW_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.POPULARITY_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.POSTER_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.RELEASE_DATE_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.TITLE_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.TvShowColumns.VOTE_TV;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.getColumnInt;
import static com.atsdev.moviecataloguedb.database.DatabaseContract.getColumnString;

public class TvShowItem implements Parcelable {

    private int id;
    private String poster;
    private String name;
    private String firstAirDate;
    private String voteAverage;
    private String popularity;
    private String originalLanguage;
    private String originalName;
    private String overview;

    public int getId() {
        return id;
    }

    public String getPoster() {
        return poster;
    }

    public String getName() {
        return name;
    }

    public String getFirstAirDate() {
        return firstAirDate;
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

    public String getOriginalName() {
        return originalName;
    }

    public String getOverview() {
        return overview;
    }

    public TvShowItem (JSONObject object) {

        try {
            this.id = object.getInt("id");
            String image = object.getString("poster_path");
            this.poster = "https://image.tmdb.org/t/p/w185/" + image;
            this.name = object.getString("name");
            this.firstAirDate = object.getString("first_air_date");
            this.voteAverage = object.getString("vote_average");
            this.popularity = object.getString("popularity");
            this.originalLanguage = object.getString("original_language");
            this.originalName = object.getString("original_name");
            this.overview = object.getString("overview");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Error Data", e.getMessage());
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
        dest.writeString(this.name);
        dest.writeString(this.firstAirDate);
        dest.writeString(this.voteAverage);
        dest.writeString(this.popularity);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.originalName);
        dest.writeString(this.overview);
    }

    private TvShowItem(Parcel in) {
        this.id = in.readInt();
        this.poster = in.readString();
        this.name = in.readString();
        this.firstAirDate = in.readString();
        this.voteAverage = in.readString();
        this.popularity = in.readString();
        this.originalLanguage = in.readString();
        this.originalName = in.readString();
        this.overview = in.readString();
    }

    public static final Creator<TvShowItem> CREATOR = new Creator<TvShowItem>() {
        @Override
        public TvShowItem createFromParcel(Parcel source) {
            return new TvShowItem(source);
        }

        @Override
        public TvShowItem[] newArray(int size) {
            return new TvShowItem[size];
        }
    };

    @NotNull
    @Override
    public String toString() {
        return "MovieItem{" +
                "id = '" + id + "" +
                ",poster_path = '" + poster + "" +
                ",name = '" + name + "" +
                ",first_air_date = '" + firstAirDate + "" +
                ",vote_average = '" + voteAverage + "" +
                ",popularity = '" + popularity + "" +
                ",original_language = '" + originalLanguage + "" +
                ",original_title = '" + originalName + "" +
                ",overview = '" + overview + "" +
                "}";
    }

    public TvShowItem (Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.poster = getColumnString(cursor, POSTER_TV);
        this.name = getColumnString(cursor, TITLE_TV);
        this.firstAirDate = getColumnString(cursor, RELEASE_DATE_TV);
        this.voteAverage = getColumnString(cursor, VOTE_TV);
        this.popularity = getColumnString(cursor, POPULARITY_TV);
        this.originalLanguage = getColumnString(cursor, ORIGINAL_LANGUAGE_TV);
        this.originalName = getColumnString(cursor, ORIGINAL_TITLE_TV);
        this.overview = getColumnString(cursor, OVERVIEW_TV);
    }
}
