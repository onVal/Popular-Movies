package com.onval.popular_movies;

/**
 * Created by gval on 14/06/2017.
 */

public class GridPresenter {
    private GridInterface view;
    private MovieFetcher fetcher;

    GridPresenter(GridInterface view) {
        this.view = view;
        this.fetcher = new MovieFetcher();
    }
}
