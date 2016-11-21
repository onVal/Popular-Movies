package com.onval.popular_movies_1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;

public class GridFragment extends Fragment {
    final static String PICASSO_URL = "http://i.imgur.com/DvpvklR.png";
    final static String CAT_URL = "https://www.petdrugsonline.co.uk/images/page-headers/cats-master-header";

    public ThumbnailAdapter adapter;

    public GridFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //four picasso images, just to populate it with something
        String[] urls = {
            PICASSO_URL, CAT_URL, PICASSO_URL, CAT_URL
        };

        adapter = new ThumbnailAdapter(getContext(), new ArrayList<>(Arrays.asList(urls)));

        GridView gridView = (GridView) rootView.findViewById(R.id.grid_view);
        gridView.setAdapter(adapter);

        return rootView;
    }
}
