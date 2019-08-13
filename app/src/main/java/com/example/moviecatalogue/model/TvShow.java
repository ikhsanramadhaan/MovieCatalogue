package com.example.moviecatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TvShow implements Parcelable {
    @SerializedName("original_name")
    private String original_name;

    @SerializedName("vote_count")
    private String vote_count;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster_path")
    private String poster_path;

    @SerializedName("first_air_date")
    private String first_air_date;

    @SerializedName("backdrop_path")
    private String backdrop_path;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("popularity")
    private double popularity;

    protected TvShow(Parcel in) {
        original_name = in.readString();
        vote_count = in.readString();
        overview = in.readString();
        poster_path = in.readString();
        first_air_date = in.readString();
        backdrop_path = in.readString();
        voteAverage = in.readDouble();
        popularity = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(original_name);
        dest.writeString(vote_count);
        dest.writeString(overview);
        dest.writeString(poster_path);
        dest.writeString(first_air_date);
        dest.writeString(backdrop_path);
        dest.writeDouble(voteAverage);
        dest.writeDouble(popularity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel in) {
            return new TvShow(in);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }
}
