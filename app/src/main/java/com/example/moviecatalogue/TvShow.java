package com.example.moviecatalogue;

import android.os.Parcel;
import android.os.Parcelable;

public class TvShow implements Parcelable {
    private int photo;
    private String judul, deskripsi, tahun,genre, director;

    protected TvShow(Parcel in) {
        photo = in.readInt();
        judul = in.readString();
        deskripsi = in.readString();
        tahun = in.readString();
        genre = in.readString();
        director = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(photo);
        parcel.writeString(judul);
        parcel.writeString(deskripsi);
        parcel.writeString(tahun);
        parcel.writeString(genre);
        parcel.writeString(director);
    }

    public TvShow() {
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}
