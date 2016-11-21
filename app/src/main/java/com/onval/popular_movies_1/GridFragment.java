package com.onval.popular_movies_1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GridFragment extends Fragment {
    public ArrayAdapter<String> adapter;
    public String[] data = {
            "derp", "lol", "derpino",
            "lollino", "lollone", "ahah",
            "well", "wellone"
    };

    public List<String> stringList = new ArrayList<>(Arrays.asList(data));

    public GridFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        adapter = new ArrayAdapter<>(getContext(), R.layout.textview, stringList);
        GridView gridView = (GridView) rootView.findViewById(R.id.grid_view);
        gridView.setAdapter(adapter);

        return rootView;
    }
}
