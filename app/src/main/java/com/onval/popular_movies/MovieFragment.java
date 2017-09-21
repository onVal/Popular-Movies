package com.onval.popular_movies;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.onval.popular_movies.Activities.DetailActivity;
import com.onval.popular_movies.Adapters.RecyclerAdapter;
import com.onval.popular_movies.Presenter.MoviePresenter;
import com.onval.popular_movies.Utilities.Utilities;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieFragment extends Fragment implements
        GridInterface, ItemClickInterface {

    private final String LOG_TAG = MovieFragment.class.getSimpleName();

    private final int COL_SHOWN_IN_PORTRAIT = 3;
    private final int COL_SHOWN_IN_LANDSCAPE = 5;

    private Context mContext;

    //Data
    public ArrayList<MovieDetail> mMovieDetailsArray = new ArrayList<>();

    //Views
    @BindView(R.id.grid_view) StatefulRecyclerView moviesRecyclerView;

    private RecyclerAdapter moviesAdapter;
    private GridLayoutManager layoutManager;

    //Presenter
    private MoviePresenter presenter;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle(R.string.app_name);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layoutManager.setSpanCount(COL_SHOWN_IN_PORTRAIT);
        else
            layoutManager.setSpanCount(COL_SHOWN_IN_LANDSCAPE);

        if (moviesAdapter != null) {
            presenter.sortMovies(mContext, mMovieDetailsArray, Utilities.getSortOption(mContext));
            moviesAdapter.notifyDataSetChanged();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);

        layoutManager = new GridLayoutManager(mContext, COL_SHOWN_IN_PORTRAIT);

        moviesRecyclerView.setLayoutManager(layoutManager);

        presenter = new MoviePresenter(this);

        if (mMovieDetailsArray.size() == 0)
            presenter.fetchMoviesAsync();

        if (moviesAdapter == null)
            moviesAdapter = new RecyclerAdapter(mContext, mMovieDetailsArray, this); //todo:maybe refactor this method

        return rootView;
    }

    //Called when a movie is selected
    @Override
    public void onItemClick(RecyclerAdapter.MoviePosterHolder holder) {
        int position = holder.getAdapterPosition();
        Intent intent = new Intent(mContext, DetailActivity.class);
        intent.putExtra(MovieDetail.MOVIE_DETAILS_ID, mMovieDetailsArray.get(position));
        startActivity(intent);
    }

    @Override
    public void showErrorMessage(VolleyError error) {
        Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initializeAdapter() {
        if (moviesAdapter == null)
            moviesAdapter = new RecyclerAdapter(mContext, mMovieDetailsArray, this);
        moviesRecyclerView.setAdapter(moviesAdapter);
    }
}

