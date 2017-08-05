package com.onval.popular_movies;

/**
 * Created by gval on 24/07/2017.
 */

public interface ItemClickInterface {
    void onItemClick(RecyclerAdapter.MoviePosterHolder holder);
    void onItemClick(CursorRecyclerAdapter.FavPosterHolder holder);
}
