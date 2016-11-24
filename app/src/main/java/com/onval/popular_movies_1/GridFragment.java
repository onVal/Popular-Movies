package com.onval.popular_movies_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class GridFragment extends Fragment {
    final static String PICASSO_URL = "http://i.imgur.com/DvpvklR.png";
    final static String CAT_URL = "https://www.petdrugsonline.co.uk/images/page-headers/cats-master-header";
    static ArrayList<String> urls = new ArrayList<>();

    public ThumbnailAdapter adapter;

    public GridFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //four picasso images, just to populate it with something
//        String[] urls = {
//            PICASSO_URL, CAT_URL, PICASSO_URL, CAT_URL
//        };

        /* result has to be the movie posters urls
         * result = asynctask.execute();
         * result.getPosterPath() <- for each elem
         * NEED TO ADD url path processing inside the adapter (or outside???)
         */

        try {
            new FetchPopularMoviesTask().execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter = new ThumbnailAdapter(getContext(), urls);

        GridView gridView = (GridView) rootView.findViewById(R.id.grid_view);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getContext(), DetailActivity.class));
            }
        });

        return rootView;
    }
}
