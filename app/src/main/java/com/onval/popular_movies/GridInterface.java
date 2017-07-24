package com.onval.popular_movies;

import com.android.volley.VolleyError;

/**
 * Created by gval on 14/06/2017.
 */

public interface GridInterface {
    void showErrorMessage(VolleyError error);
    void initializeAdapter();
    void favoritesMenuClicked();
}
