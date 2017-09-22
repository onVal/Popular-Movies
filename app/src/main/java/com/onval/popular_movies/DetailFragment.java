package com.onval.popular_movies;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.onval.popular_movies.Presenter.DetailPresenter;
import com.onval.popular_movies.Utilities.Utilities;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment implements DetailInterface {
    private static final String SCROLL_POSITION = "current-scroll-position";

    private static final String REVIEWS_HAVE_BEEN_LOADED = "loaded-reviews";
    private static final String REVIEWS_ARE_VISIBLE = "visible-reviews";


    @BindView(R.id.thumbnail)       ImageView thumbnail;
    @BindView(R.id.title)           TextView titleView;
    @BindView(R.id.release_date)    TextView releaseDateView;
    @BindView(R.id.overview)        TextView overview;
    @BindView(R.id.vote_average)    TextView voteAverage;
    @BindView(R.id.favorites)       ImageView favoritesStar;
    @BindView(R.id.markasfav)       TextView favoritesText;
    @BindView(R.id.trailers_list)   LinearLayout trailerList;
    @BindView(R.id.reviews_list)    LinearLayout reviewList;
    @BindView(R.id.show_reviews)    TextView showReview;

    private boolean reviewsHaveBeenLoaded;
    private boolean reviewsAreVisible;

    private Context context;
    private DetailPresenter presenter;
    private MovieDetail movieDetail;

    //Reference used to be able to update the scroll position
    private ScrollView scrollView;
    private int[] scrollPosition;

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

        //Update fav text and icons accordingly and set listener
        onLoadFavoriteUI();

        favoritesStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.favoriteClicked(context, movieDetail);
            }
        });

        //Load trailers dynamically making a call to the server
        presenter.loadTrailers(context, movieDetail);

        //Load reviews on click
        showReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!reviewsHaveBeenLoaded) {
                    presenter.loadReviews(context, movieDetail);
                    reviewsHaveBeenLoaded = true; //this will stay true so this condition will be executed only once

                    // update showReview text
                    showReview.setText("hide reviews");
                    reviewsAreVisible = true;
                    return;
                }

                toggleReviewVisibility();
            }
        });


        //Code to deal with ScrollView retaining its scroll position
        if (rootView instanceof ScrollView)
            scrollView = (ScrollView) rootView;

        //Code to let reviews be visible even on config changes
        if (savedInstanceState != null) {
            reviewsHaveBeenLoaded = savedInstanceState.getBoolean(REVIEWS_HAVE_BEEN_LOADED);
            reviewsAreVisible = savedInstanceState.getBoolean(REVIEWS_ARE_VISIBLE);
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        //Recover from previous position
        if (reviewsAreVisible) {
            presenter.loadReviews(context, movieDetail);
            showReview.setText("hide reviews");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putIntArray(SCROLL_POSITION,
                new int[] {scrollView.getScrollX(), scrollView.getScrollY()});

        outState.putBoolean(REVIEWS_HAVE_BEEN_LOADED, reviewsHaveBeenLoaded);
        outState.putBoolean(REVIEWS_ARE_VISIBLE, reviewsAreVisible);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null)
            scrollPosition = savedInstanceState.getIntArray(SCROLL_POSITION);
    }

    private void toggleReviewVisibility() {
        if (reviewsAreVisible) {
            reviewList.setVisibility(View.GONE);
            showReview.setText("show reviews");

            reviewsAreVisible = false;
        }
        else {
            reviewList.setVisibility(View.VISIBLE);
            showReview.setText("hide reviews");

            reviewsAreVisible = true;
        }
    }

    @Override
    public void onLoadTrailer(String name, final String key) {
        //noinspection RestrictedApi
        View trailer = getLayoutInflater(null).inflate(R.layout.trailer_item, trailerList, false);
        TextView trailerName = (TextView) trailer.findViewById(R.id.trailer_title_id);
        trailerName.setText(name);

        trailerList.addView(trailer);

        trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Utilities.createYoutubeUri(key);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(uri);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onLoadReviews(String user, String review_content) {
        //noinspection RestrictedApi
        View review = getLayoutInflater(null).inflate(R.layout.review_item, reviewList, false);

        TextView userView = (TextView) review.findViewById(R.id.user);
        TextView reviewContent = (TextView) review.findViewById(R.id.review_content);

        userView.setText(user);
        reviewContent.setText(review_content);

        reviewList.addView(review);
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

    @Override
    public void restoreScrollPosition() {
        if(scrollPosition != null) {
            scrollView.post(new Runnable() {
                public void run() {
                    scrollView.scrollTo(scrollPosition[0], scrollPosition[1]);
                }
            });
        }
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
