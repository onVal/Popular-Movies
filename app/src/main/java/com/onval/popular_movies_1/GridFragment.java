package com.onval.popular_movies_1;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class GridFragment extends Fragment {
    ArrayList<MovieDetail> movieDetails = new ArrayList<>();

    public ThumbnailAdapter adapter;

    public GridFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        String sortOption = PreferenceManager.getDefaultSharedPreferences(getContext())
                .getString(getString(R.string.pref_sort_key),
                        getString(R.string.pref_popularity_value));

        Log.d("TAG", sortOption);

        try {
            movieDetails = new FetchPopularMoviesTask().execute(sortOption).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // extract urls arraylist here from movieDetails arrayList...or NOT
        adapter = new ThumbnailAdapter(getContext(), movieDetails);

        GridView gridView = (GridView) rootView.findViewById(R.id.grid_view);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MovieDetail clickedView = ((MovieDetail) adapterView.getItemAtPosition(i));

                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("com.onval.popular_movies_1.DetailClass", clickedView);

                startActivity(intent);
            }
        });

        return rootView;
    }
}
