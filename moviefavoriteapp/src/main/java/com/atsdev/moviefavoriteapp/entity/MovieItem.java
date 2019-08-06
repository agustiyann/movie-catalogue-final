package com.atsdev.moviefavoriteapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

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

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
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
}
