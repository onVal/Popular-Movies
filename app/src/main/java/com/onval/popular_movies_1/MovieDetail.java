package com.onval.popular_movies_1;

/**
 * Created by gval on 23/11/16.
 */

public class MovieDetail {
    private String posterPath;
    private String overview;
    private double vote_average;
    private String release_date;

    public MovieDetail(String posterPath, String overview, double vote_average, String release_date) {
        this.posterPath = posterPath;
        this.overview = overview;
        this.vote_average = vote_average;
        this.release_date = release_date;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
