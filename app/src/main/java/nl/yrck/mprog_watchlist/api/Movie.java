package nl.yrck.mprog_watchlist.api;


import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Movie implements Parcelable {
    private String title;
    private String year;
    private String imdbID;
    private String type;
    private String poster;

    @JsonCreator
    public Movie(
            @JsonProperty("Title") String title,
            @JsonProperty("Year") String year,
            @JsonProperty("imdbID") String imdbID,
            @JsonProperty("Type") String type,
            @JsonProperty("Poster") String poster) {
        this.title = title;
        this.year = year;
        this.imdbID = imdbID;
        this.type = type;
        this.poster = poster;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }


    public Movie(Parcel parcel) {
        title = parcel.readString();
        year = parcel.readString();
        imdbID = parcel.readString();
        type = parcel.readString();
        poster = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(year);
        parcel.writeString(imdbID);
        parcel.writeString(type);
        parcel.writeString(poster);
    }

}