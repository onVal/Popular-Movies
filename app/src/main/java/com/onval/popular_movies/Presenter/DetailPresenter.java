package com.onval.popular_movies.Presenter;

import com.onval.popular_movies.DetailView;

/**
 * Created by gval on 18/06/2017.
 */

public class DetailPresenter implements DetailPresenterInterface {
    DetailView view;

    public DetailPresenter(DetailView view) {
        this.view = view;
    }

    @Override
    public void favoriteClicked() {

    }
}
