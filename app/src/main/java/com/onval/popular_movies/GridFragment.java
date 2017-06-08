package com.onval.popular_movies;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.onval.popular_movies.Utilities.FetchUtilities;

import org.json.JSONObject;

import java.util.ArrayList;

import static com.onval.popular_movies.Utilities.FetchUtilities.sortMovies;

public class GridFragment extends Fragment implements
        Response.Listener<JSONObject>, Response.ErrorListener, RequestQueue.RequestFinishedListener<JSONObject> {

    private Context mContext;
    private final String LOG_TAG = GridFragment.class.getSimpleName();

    private ArrayList<MovieDetail> mMovieDetailsArray = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;

    private int mPageToFetch = 0;


    public GridFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mRecyclerAdapter != null) {
            Log.d(LOG_TAG, "Calling onResume");
            sortMovies(mMovieDetailsArray, mContext);
            mRecyclerAdapter.notifyDataSetChanged();

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.grid_view);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3);
        mRecyclerView.setLayoutManager(layoutManager);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        fetchNextPage();

        //debug
        if (mMovieDetailsArray.isEmpty())
            Log.d(LOG_TAG, "The array is empty after fetchMoviesToArray");
        else
            Log.d(LOG_TAG, "The array has " + mMovieDetailsArray.size() + " elements after fetching");


//        mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                MovieDetail clickedView = ((MovieDetail) adapterView.getItemAtPosition(i));
//
//                Intent intent = new Intent(mContext, DetailActivity.class);
//                intent.putExtra("com.onval.popular_movies_1.DetailClass", clickedView);
//
//                startActivity(intent);
//            }
//        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchNextPage();
            }
        });

        return rootView;
    }

    // Use volley library to fetch movie data in JSON format
    private void fetchNextPage() {
        final RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.addRequestFinishedListener(this);

        ++mPageToFetch;

        String singlePageURL = FetchUtilities.createMoviesUri(mPageToFetch).toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                singlePageURL,  // string url
                null,           // optional JSONObject parameter (?)
                this,           //onResponse
                this            //onError
        );
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        FetchUtilities.addMoviesFromJSON(response, mMovieDetailsArray);
        sortMovies(mMovieDetailsArray, mContext);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //TODO: To implement
        Log.e(LOG_TAG, "onErrorResponse: an error has occurred.");
    }

    @Override
    public void onRequestFinished(Request<JSONObject> request) {
        if (mRecyclerAdapter == null) {
            mRecyclerAdapter = new RecyclerAdapter(mContext, mMovieDetailsArray);
        }

        mRecyclerView.setAdapter(mRecyclerAdapter);
    }
}
