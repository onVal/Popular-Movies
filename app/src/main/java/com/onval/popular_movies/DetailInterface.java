package com.onval.popular_movies;

/**
 * Created by gval on 18/06/2017.
 */

public interface DetailInterface {
    void onMarkFavorite();
    void onRemoveFavorite();
    void onLoadTrailer(String name, String key);
    void onLoadReviews(String user, String review);
    void restoreScrollPosition();
}
