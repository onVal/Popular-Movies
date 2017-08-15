package com.onval.popular_movies;

import com.onval.popular_movies.Adapters.FavoritesAdapter;
import com.onval.popular_movies.Adapters.RecyclerAdapter;

/**
 * Created by gval on 24/07/2017.
 */

public interface ItemClickInterface {
    void onItemClick(RecyclerAdapter.MoviePosterHolder holder);
    void onItemClick(FavoritesAdapter.FavPosterHolder holder);
}
