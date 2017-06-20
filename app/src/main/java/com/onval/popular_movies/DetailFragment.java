package com.onval.popular_movies;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onval.popular_movies.Presenter.DetailPresenter;
import com.onval.popular_movies.Provider.MovieContract;
import com.onval.popular_movies.Utilities.Utilities;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment implements DetailView, View.OnClickListener {
    @BindView(R.id.thumbnail)       ImageView thumbnail;
    @BindView(R.id.title)           TextView titleView;
    @BindView(R.id.release_date)    TextView releaseDateView;
    @BindView(R.id.overview)        TextView overview;
    @BindView(R.id.vote_average)    TextView voteAverage;
    @BindView(R.id.favorites)       ImageView favoritesStar;
    @BindView(R.id.markasfav)       TextView favoritesText;

    private final Context context = getContext();
    private DetailPresenter presenter;
    MovieDetail movieDetail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new DetailPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        // Initialize movieDetail object with data received from intent
        movieDetail = getActivity().getIntent().getParcelableExtra(MovieDetail.MOVIE_DETAILS_ID);

        ButterKnife.bind(this, rootView);

        //Load image to thumbnail TODO: change this, too slow
        String imageURLPath = Utilities.IMAGE_URL + movieDetail.getPosterPath();
        Picasso.with(context).load(imageURLPath).into(thumbnail);

        //Set text elements
        titleView.setText(movieDetail.getTitle());
        releaseDateView.setText(movieDetail.getRelease_date());
        overview.setText(movieDetail.getOverview());
        overview.setMovementMethod(new ScrollingMovementMethod());
        voteAverage.setText(String.valueOf(movieDetail.getVote_average()) + " / 10");

        // TODO: if this movie's favorite is in content provider, put yellow star and dim text
        // TODO: if not, put gray star and make text brighter
        favoritesStar.setImageResource(R.drawable.empty_star);
        favoritesText.setText(getString(R.string.mark_as_favorite));

        favoritesStar.setOnClickListener(this);

        return rootView;
    }

    @Override  //From View.OnClickListener TODO: make presenter do this
    public void onClick(View view) {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(MovieContract.FAVORITES_URI,
                null, null, null, null);

        if (cursor.moveToFirst()) {

            onRemoveFavorite();
        }
        else {
            onMarkFavorite();
        }
    }

    @Override
    public void onMarkFavorite() {
        ContentResolver resolver = context.getContentResolver();
        // if movie is FAVORITE: update ui - call INSERT on provider
//        String movie_id = movieDetail.getMovie_id() + "";
//        resolver.insert(MovieContract.FAVORITES_URI.buildUpon().appendPath(movie_id).build(), null);

        // TODO: update ui
    }

    @Override
    public void onRemoveFavorite() {
        ContentResolver resolver = context.getContentResolver();

        // if movie is NOT FAVORITE: update ui - call DELETE on provider
        resolver.delete(MovieContract.FAVORITES_URI, null, null);

        // TODO: update ui
    }
}
