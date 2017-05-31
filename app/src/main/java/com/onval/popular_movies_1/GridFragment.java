package com.onval.popular_movies_1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.onval.popular_movies_1.Utilities.FetchUtilities;

import java.util.ArrayList;

public class GridFragment extends Fragment {
    private Context context;

    public static ArrayList<MovieDetail> movieDetails = new ArrayList<>();

    private ThumbnailAdapter adapter;

    public GridFragment() {
        // Required empty public constructor
    }

    private void fetchFromMovieDb() {
        String sortOption = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(getString(R.string.pref_sort_key),
                        getString(R.string.pref_popularity_value));

        try {
            new FetchPopularMoviesTask(context).execute(sortOption);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

//        if (adapter != null)
//            fetchFromMovieDb();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.grid_view);
        context = getContext();

        FetchUtilities.fetchMoviesToArray(context);
        adapter = new ThumbnailAdapter(context, movieDetails);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MovieDetail clickedView = ((MovieDetail) adapterView.getItemAtPosition(i));

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("com.onval.popular_movies_1.DetailClass", clickedView);

                startActivity(intent);
            }
        });

        return rootView;
    }
}
