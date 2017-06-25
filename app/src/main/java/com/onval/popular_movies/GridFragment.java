package com.onval.popular_movies;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.onval.popular_movies.Activities.DetailActivity;
import com.onval.popular_movies.Activities.MyPreferenceActivity;
import com.onval.popular_movies.Presenter.GridPresenter;
import com.onval.popular_movies.Provider.MovieContract;
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
    public ArrayList<MovieDetail> favMoviesArray = new ArrayList<>();

    @BindView(R.id.grid_view) RecyclerView mRecyclerView;
    @BindView(R.id.fab) FloatingActionButton loadMoreMoviesFab;

    private RecyclerAdapter mRecyclerAdapter;
    private RecyclerAdapter favoritesAdapter;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        presenter.fetchMoviesAsync();

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            startActivity(new Intent(getActivity(), MyPreferenceActivity.class));
            return true;
        }
        if (item.getItemId() == R.id.favorites) { //TODO: THIS IS GARBAGE!
//            presenter.onMenuFavorite();
            Cursor c = getContext().getContentResolver().query(MovieContract.Favorites.CONTENT_URI, null, null, null, null);

            if (c != null && c.moveToFirst()) {
                do {
                    int columnId = c.getColumnIndexOrThrow(MovieContract.Favorites._ID);
                    int movieId = c.getInt(columnId);

//                    mMovieDetailsArray.get()

//                    favMoviesArray.add();
                } while (c.moveToNext());
            }

            if (favoritesAdapter == null) {
                favoritesAdapter = new RecyclerAdapter(getContext(), favMoviesArray, this);
            }

            mRecyclerView.swapAdapter(favoritesAdapter, true);
        }

        return super.onOptionsItemSelected(item);
    }
}

