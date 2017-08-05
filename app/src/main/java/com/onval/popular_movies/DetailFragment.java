package com.onval.popular_movies;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
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
import com.onval.popular_movies.Utilities.Utilities;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment implements DetailInterface {
    @BindView(R.id.thumbnail)       ImageView thumbnail;
    @BindView(R.id.title)           TextView titleView;
    @BindView(R.id.release_date)    TextView releaseDateView;
    @BindView(R.id.overview)        TextView overview;
    @BindView(R.id.vote_average)    TextView voteAverage;
    @BindView(R.id.favorites)       ImageView favoritesStar;
    @BindView(R.id.markasfav)       TextView favoritesText;

    private Context context;
    private DetailPresenter presenter;
    private MovieDetail movieDetail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new DetailPresenter(this);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
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
        voteAverage.setText(String.valueOf(movieDetail.getVoteAverage()) + " / 10");

        onLoadFavoriteUI();

        favoritesStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.favoriteClicked(context, movieDetail);
            }
        });

        return rootView;
    }

    @Override
    public void onMarkFavorite() {
        favoritesStar.setImageResource(R.drawable.full_star);
        favoritesText.setText(getString(R.string.rm_from_favorite));
        favoritesText.setTextColor(Color.GRAY);
    }

    @Override
    public void onRemoveFavorite() {
        favoritesStar.setImageResource(R.drawable.empty_star);
        favoritesText.setText(getString(R.string.mark_as_favorite));
        favoritesText.setTextColor(Color.WHITE);
    }

    private void onLoadFavoriteUI() {

        // Checks if given movie is a favorite or not
        Cursor cursor = context.getContentResolver().query(Utilities.buildUriWithId(movieDetail.getId()),
                                                                        null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            onMarkFavorite();
        } else {
            onRemoveFavorite();
        }
    }
}
