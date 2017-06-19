package com.onval.popular_movies.Presenter;

import org.json.JSONObject;

/**
 * Created by gval on 15/06/2017.
 */

public interface PresenterInterface {
    void processJSONResponse(JSONObject response);
    void onMenuFavorite();
}
