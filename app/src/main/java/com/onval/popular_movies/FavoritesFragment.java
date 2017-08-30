package com.onval.popular_movies;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onval.popular_movies.Activities.DetailActivity;
import com.onval.popular_movies.Adapters.FavoritesAdapter;
import com.onval.popular_movies.Provider.MovieContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gval on 15/08/2017.
 */

public class FavoritesFragment extends Fragment implements FavItemClickInterface {

    @BindView(R.id.fav_view) RecyclerView favView;

    public Cursor favCursor;
    private Context context;

//    private FavoritesPresenter favPresenter;

    private FavoritesAdapter favAdapter;
    private GridLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_fav, container, false);
        ButterKnife.bind(this, rootView);

        context = getContext();

        favCursor = context.getContentResolver().query(MovieContract.Favorites.CONTENT_URI, null, null, null, null);
        layoutManager = new GridLayoutManager(context, 3); // todo: fix span count

        favAdapter = new FavoritesAdapter(context, favCursor, this);

        favView.setLayoutManager(layoutManager);
        favView.setAdapter(favAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
//        favAdapter.updateCursor();
//        favAdapter.notifyDataSetChanged();

        //todo: this works in updating correctly the fav fragment...but I definitely don't like it!
        favCursor = context.getContentResolver().query(MovieContract.Favorites.CONTENT_URI, null, null, null, null);
        favAdapter = new FavoritesAdapter(context, favCursor, this);
        favView.setAdapter(favAdapter);
    }

    @Override
    public void onItemClick(FavoritesAdapter.FavPosterHolder holder) {
        int position = holder.getAdapterPosition();
        Intent intent = new Intent(context, DetailActivity.class);

        MovieDetail movieDetail = movieDetailFromCursor(favCursor, position); //todo: ok this is bad design...

        intent.putExtra(MovieDetail.MOVIE_DETAILS_ID, movieDetail);
        startActivity(intent);
    }

    private MovieDetail movieDetailFromCursor(Cursor cursor, int position) {
        cursor.moveToPosition(position);

        return new MovieDetail(
                cursor.getInt(cursor.getColumnIndex(MovieContract.Favorites._ID)),
                cursor.getString(cursor.getColumnIndex(MovieContract.Favorites.TITLE_COLUMN)),
                cursor.getString(cursor.getColumnIndex(MovieContract.Favorites.POSTERPATH_COLUMN)),
                cursor.getString(cursor.getColumnIndex(MovieContract.Favorites.OVERVIEW_COLUMN)),
                cursor.getDouble(cursor.getColumnIndex(MovieContract.Favorites.VOTE_AVG_COLUMN)),
                cursor.getDouble(cursor.getColumnIndex(MovieContract.Favorites.POPULARITY_COLUMN)),
                cursor.getString(cursor.getColumnIndex(MovieContract.Favorites.RELEASE_DATE_COLUMN))
        );
    }
}
