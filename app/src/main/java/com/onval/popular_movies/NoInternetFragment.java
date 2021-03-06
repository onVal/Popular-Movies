package com.onval.popular_movies;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.onval.popular_movies.Activities.MainActivity;
import com.onval.popular_movies.Utilities.Utilities;

/**
 * Created by gval on 28/11/16.
 */

public class NoInternetFragment extends Fragment implements View.OnClickListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_no_internet, container, false);
        Button btn = (Button) rootView.findViewById(R.id.refresh_button);
        btn.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        /* When the retry button is clicked, check again for internet connection.
         * If there is one, replace this fragment with the MovieFragment */
        if (Utilities.isOnline(getActivity().getApplicationContext())) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new MovieFragment(), MainActivity.MOVIE_FRAGMENT_TAG)
                    .commit();
        }
        else {
            Toast.makeText(getContext(), "Still no connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //do nothing
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
