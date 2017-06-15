package com.onval.popular_movies.Model;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.onval.popular_movies.GridFragment;
import com.onval.popular_movies.GridInterface;
import com.onval.popular_movies.MovieDetail;
import com.onval.popular_movies.Presenter.PresenterInterface;
import com.onval.popular_movies.Utilities.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by gval on 14/06/2017.
 */

public class MovieFetcher implements
        Response.Listener<JSONObject>, Response.ErrorListener,
        RequestQueue.RequestFinishedListener<JSONObject> {
    private final String LOG_TAG = MovieFetcher.class.getSimpleName();

    private int mPageToFetch;

    private GridInterface gridInterface;
    private PresenterInterface presenterInterface;

    public MovieFetcher(GridInterface gridInterface, PresenterInterface presenterInterface) {
        this.gridInterface = gridInterface;
        this.presenterInterface = presenterInterface;
    }

    // Use volley library to fetch movie data in JSON format
    public void fetchNextPage() {

        Context context = ((GridFragment)gridInterface).getContext();

        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.addRequestFinishedListener(this);

        ++mPageToFetch;

        String singlePageURL = Utilities.createMoviesUri(mPageToFetch).toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                singlePageURL,  // string url
                null,           // optional JSONObject parameter (?)
                this,           //onResponse
                this            //onError
        );
        requestQueue.add(jsonObjectRequest);
    }

    // creates the arrayList of movie details from the json data
    public void addMoviesFromJSON(JSONObject jsonObject, ArrayList<MovieDetail> movieDetails) {
        String title;
        String posterPath; //url path
        String overview;
        double vote_average;
        double popularity;
        String release_date;

        try {
            JSONArray jsonResultsArray = jsonObject.getJSONArray("results");

            for (int i = 0; i < jsonResultsArray.length(); i++) {
                JSONObject jsonCurrentElement = jsonResultsArray.getJSONObject(i);

                title = jsonCurrentElement.getString("title");
                posterPath = jsonCurrentElement.getString("poster_path");
                overview = jsonCurrentElement.getString("overview");
                vote_average = jsonCurrentElement.getDouble("vote_average");
                popularity = jsonCurrentElement.getDouble("popularity");
                release_date = jsonCurrentElement.getString("release_date");

                movieDetails.add(new MovieDetail(title, posterPath, overview, vote_average, popularity, release_date));
            }
        } catch (JSONException | NullPointerException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        presenterInterface.processJSONResponse(response);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        gridInterface.showErrorMessage(error);
    }

    @Override
    public void onRequestFinished(Request<JSONObject> request) {
            gridInterface.initializeAdapter();
    }
}
