package com.onval.popular_movies_1;

import java.io.Serializable;

/**
 * Created by gval on 23/11/16.
 */

public class MovieDetail implements Serializable {
    private String title;
    private String posterPath;
    private String overview;
    private double vote_average;
    private String release_date;

    public MovieDetail(String title, String posterPath,
                       String overview, double vote_average, String release_date) {
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.vote_average = vote_average;
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }
}
