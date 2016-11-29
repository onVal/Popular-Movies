package com.onval.popular_movies_1;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by gval on 28/11/16.
 */

final class CheckConnection {
    private CheckConnection() {
    }

    /* This was taken from the stackoverflow answer suggested in the project detail */
    static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}

public class NoInternetFragment extends Fragment {
    public NoInternetFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_no_internet, container, false);
        Button btn = (Button) rootView.findViewById(R.id.refresh_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* When the retry button is clicked, check again for internet connection.
                 * If there is, replace this fragment with the GridFragment
                 */
                if (CheckConnection.isOnline(getActivity())) {
                    getFragmentManager().beginTransaction()
                            .replace(R.id.activity_main, new GridFragment())
                            .commit();
                }
                else {
                    Toast.makeText(getActivity(), "Still no connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
