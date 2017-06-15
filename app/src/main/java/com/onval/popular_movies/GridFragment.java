package com.onval.popular_movies;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.onval.popular_movies.Utilities.Utilities;

import java.util.ArrayList;

import butterknife.BindView;

public class GridFragment extends Fragment implements
        GridInterface, View.OnClickListener, RecyclerAdapter.ItemClickInterface {

    private final String LOG_TAG = GridFragment.class.getSimpleName();

    private final int COL_SHOWN_IN_PORTRAIT = 3;
    private final int COL_SHOWN_IN_LANDSCAPE = 5;

    private Context mContext;

    public ArrayList<MovieDetail> mMovieDetailsArray = new ArrayList<>();

    @BindView(R.id.grid_view) RecyclerView mRecyclerView;
    @BindView(R.id.fab) FloatingActionButton loadMoreMoviesFab;

    public RecyclerAdapter mRecyclerAdapter;
    private GridLayoutManager layoutManager;

    private GridPresenter presenter;

    public GridFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layoutManager.setSpanCount(COL_SHOWN_IN_PORTRAIT);
        else
            layoutManager.setSpanCount(COL_SHOWN_IN_LANDSCAPE);

        if (mRecyclerAdapter != null) {
            presenter.sortMovies(mContext, mMovieDetailsArray, Utilities.getSortOption(mContext));
            mRecyclerAdapter.notifyDataSetChanged(); //TODO: is this line necessary?
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.grid_view);
        layoutManager = new GridLayoutManager(mContext, COL_SHOWN_IN_PORTRAIT);
        mRecyclerView.setLayoutManager(layoutManager);

        loadMoreMoviesFab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        loadMoreMoviesFab.setOnClickListener(this);

        presenter = new GridPresenter(this);
        presenter.fetchMoviesAsync(); //before there was fetchNextPage() TODO: to implement

        return rootView;
    }

    @Override
    public void onItemClick(RecyclerAdapter.MoviePosterHolder holder) {
        int position = holder.getAdapterPosition();
        Intent intent = new Intent(mContext, DetailActivity.class);
        intent.putExtra(MovieDetail.MOVIE_DETAILS_ID, mMovieDetailsArray.get(position));
        startActivity(intent);
    }

    //onClick for FAB
    @Override
    public void onClick(View view) {
        presenter.fetchMoviesAsync();
    }

    @Override
    public void showErrorMessage(VolleyError error) {
        Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initializeAdapter() {
        if (mRecyclerAdapter == null)
            mRecyclerAdapter = new RecyclerAdapter(mContext, mMovieDetailsArray, this);
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }
}

