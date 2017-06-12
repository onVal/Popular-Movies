package com.onval.popular_movies;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onval.popular_movies.Utilities.FetchUtilities;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment {
    @BindView (R.id.thumbnail)      ImageView thumbnail;
    @BindView(R.id.title)           TextView titleView;
    @BindView(R.id.release_date)    TextView releaseDateView;
    @BindView(R.id.overview)        TextView overview;
    @BindView(R.id.vote_average)    TextView voteAverage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        // Initialize movieDetail object with data received from intent
        MovieDetail movieDetail = getActivity().getIntent().getParcelableExtra(MovieDetail.MOVIE_DETAILS_ID);

        ButterKnife.bind(this, rootView);

        //Load image to thumbnail
        Picasso.with(getContext()).load(FetchUtilities.IMAGE_URL + movieDetail.getPosterPath()).into(thumbnail);

        //Set text elements
        titleView.setText(movieDetail.getTitle());
        releaseDateView.setText(movieDetail.getRelease_date());
        overview.setText(movieDetail.getOverview());
        overview.setMovementMethod(new ScrollingMovementMethod());
        voteAverage.setText(String.valueOf(movieDetail.getVote_average()) + " / 10");

        return rootView;
    }
}
