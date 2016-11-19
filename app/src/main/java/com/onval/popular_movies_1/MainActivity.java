package com.onval.popular_movies_1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main, new GridFragment())
                    .commit();
        }
    }

    public static class GridFragment extends Fragment {
        public static ArrayAdapter<String> adapter;
        public static String[] data = {
                "derp", "lol", "derpino",
                "lollino", "lollone", "ahah",
                "well"
        };

        public static List<String> stringList = new ArrayList<>(Arrays.asList(data));

        public GridFragment() { }

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
}
