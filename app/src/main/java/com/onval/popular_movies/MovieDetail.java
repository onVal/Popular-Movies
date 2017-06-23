package com.onval.popular_movies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gval on 23/11/16.
 */

public class MovieDetail implements Parcelable {

    private int movie_id;
    private String title;
    private String posterPath;
    private String overview;
    private double vote_average;
    private double popularity;
    private String release_date;

    // Constant used to identify this class when one of its objects is being bundled into a Parcel
    public static final String MOVIE_DETAILS_ID = "com.onval.popular_movies.MovieDetails";

    // CREATOR member, required for Parcelable objects
    public static final Parcelable.Creator<MovieDetail> CREATOR =
            new Parcelable.Creator<MovieDetail>() {
                @Override
                public MovieDetail createFromParcel(Parcel parcel) {
                    return new MovieDetail(parcel);
                }

                @Override
                public MovieDetail[] newArray(int i) {
                    return new MovieDetail[i];
                }
            };

    public MovieDetail(int movie_id, String title, String posterPath, String overview,
                       double vote_average, double popularity, String release_date) {
        this.movie_id = movie_id;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.vote_average = vote_average;
        this.popularity = popularity;
        this.release_date = release_date;
    }

    // Alternative constructor used in createFromParcel
    private MovieDetail(Parcel parcel) {
        movie_id = parcel.readInt();
        title = parcel.readString();
        posterPath = parcel.readString();
        overview = parcel.readString();
        vote_average = parcel.readDouble();
        popularity = parcel.readDouble();
        release_date = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(movie_id);
        parcel.writeString(title);
        parcel.writeString(posterPath);
        parcel.writeString(overview);
        parcel.writeDouble(vote_average);
        parcel.writeDouble(popularity);
        parcel.writeString(release_date);
    }

    // Getters and setters
    public int getId() {return movie_id;}
    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public double getVoteAverage() {
        return vote_average;
    }
    public double getPopularity() { return popularity; }

    public String getRelease_date() {
        return release_date;
    }
}
