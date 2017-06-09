package com.onval.popular_movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    static MovieDetail movieDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_detail, new DetailFragment())
                    .commit();

            // Initialize movieDetail object with data received from intent
            movieDetail = (MovieDetail) getIntent().getSerializableExtra("com.onval.popular_movies.DetailClass");
        }
    }

    public static class DetailFragment extends Fragment {
        public DetailFragment() { }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            //TODO: Use ButterKnife or DataBindingLibrary instead of this

            ImageView thumbnail = (ImageView) rootView.findViewById(R.id.thumbnail);
            Picasso.with(getContext()).load("http://image.tmdb.org/t/p/" + "w342" + movieDetail.getPosterPath()).into(thumbnail);

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
}