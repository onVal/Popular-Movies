package com.onval.popular_movies_1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.onval.popular_movies_1.Utilities.FetchUtilities;

import org.json.JSONObject;

import java.util.ArrayList;

import static com.onval.popular_movies_1.Utilities.FetchUtilities.sortMovies;

public class GridFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener,
        RequestQueue.RequestFinishedListener<JSONObject> {
    private Context context;
    private final String LOG_TAG = GridFragment.class.getSimpleName();

    public static ArrayList<MovieDetail> movieDetails = new ArrayList<>();
    private GridView gridView;

    private ThumbnailAdapter adapter;

    public GridFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

        if (adapter != null) {
            fetchMoviesToArray();
//            adapter.addAll(movieDetails);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        gridView = (GridView) rootView.findViewById(R.id.grid_view);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        context = getContext();

//        FetchUtilities.clearMovieDetails();
        fetchMoviesToArray();

        //debug
        if (movieDetails.isEmpty())
            Log.d(LOG_TAG, "The array is empty after fetchMoviesToArray");
        else
            Log.d(LOG_TAG, "The array has " + movieDetails.size() + " elements after fetching");


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MovieDetail clickedView = ((MovieDetail) adapterView.getItemAtPosition(i));

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("com.onval.popular_movies_1.DetailClass", clickedView);

                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo: to implement
            }
        });



        return rootView;
    }

    // Use volley library to fetch movie data in JSON format
    private void fetchMoviesToArray() {
        final int DEFAULT_NUMBER_OF_PAGES = 2;

        movieDetails.clear();

        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        for (int i = 1; i <= DEFAULT_NUMBER_OF_PAGES; i++) {

            String singlePageURL = FetchUtilities.createMoviesUri(i).toString();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    singlePageURL,  // string url
                    null,           // optional JSONObject parameter (?)
                    this,           //onResponse
                    this            //onError
            );
            //Add the request queues sequentially
            requestQueue.add(jsonObjectRequest);
            requestQueue.addRequestFinishedListener(this);
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        FetchUtilities.addMoviesFromJSON(response);
        FetchUtilities.debug(); //tmp
        sortMovies(context);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //TODO: To implement
        Log.e(LOG_TAG, "onErrorResponse: an error has occurred.");
    }

    @Override
    public void onRequestFinished(Request<JSONObject> request) {
        if (adapter == null) {
            adapter = new ThumbnailAdapter(context);
        }

        gridView.setAdapter(adapter);
    }
}
