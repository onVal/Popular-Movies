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

public class DetailFragment extends Fragment {
    public DetailFragment() { }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        //TODO: Use ButterKnife or DataBindingLibrary instead of this

        // Initialize movieDetail object with data received from intent
        MovieDetail movieDetail = getActivity().getIntent().getParcelableExtra(MovieDetail.MOVIE_DETAILS_ID);

        ImageView thumbnail = (ImageView) rootView.findViewById(R.id.thumbnail);
        Picasso.with(getContext()).load(FetchUtilities.IMAGE_URL + movieDetail.getPosterPath()).into(thumbnail);

        TextView titleView = (TextView) rootView.findViewById(R.id.title);
        titleView.setText(movieDetail.getTitle());

        TextView releaseDateView = (TextView) rootView.findViewById(R.id.release_date);
        releaseDateView.setText(movieDetail.getRelease_date());

        TextView overview = (TextView) rootView.findViewById(R.id.overview);
        overview.setText(movieDetail.getOverview());
        overview.setMovementMethod(new ScrollingMovementMethod());

        TextView voteAverage = (TextView) rootView.findViewById(R.id.vote_average);
        voteAverage.setText(String.valueOf(movieDetail.getVote_average()) + " / 10");


        return rootView;
    }
}
