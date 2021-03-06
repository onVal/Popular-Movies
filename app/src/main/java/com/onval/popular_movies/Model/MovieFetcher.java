package com.onval.popular_movies.Model;

import android.content.Context;
import android.net.Uri;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.onval.popular_movies.MovieDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by gval on 14/06/2017.
 */

public class MovieFetcher {
    private static final String LOG_TAG = MovieFetcher.class.getSimpleName();

    private int mPageToFetch;

    // Interfaces for Volley callbacks
    // I let the Presenter handle them
    private Response.Listener<JSONObject> onResponseListener;
    private Response.ErrorListener onErrorListener;
    private RequestQueue.RequestFinishedListener<JSONObject> requestFinishedListener;

    public MovieFetcher(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener,
                        RequestQueue.RequestFinishedListener<JSONObject> requestFinishedListener) {
        this.onResponseListener = listener;
        this.onErrorListener = errorListener;
        this.requestFinishedListener = requestFinishedListener;
    }

    // Use volley library to fetch movie data in JSON format
    public void fetch(Context context, Uri uri) {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.addRequestFinishedListener(requestFinishedListener);

        String url = uri.toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                url,                        // string url
                null,                       // optional JSONObject parameter (?)
                onResponseListener,         //onResponse
                onErrorListener             //onError
        );
        requestQueue.add(jsonObjectRequest);
    }

    // creates the arrayList of movie details from the json data
    public void addMoviesFromJSON(JSONObject jsonObject, ArrayList<MovieDetail> movieDetails) {
        int movie_id;
        String title;
        String posterPath; //url path
        String overview;
        double vote_average;
        double popularity;
        String release_date;

        movieDetails.clear();

        try {
            JSONArray jsonResultsArray = jsonObject.getJSONArray("results");

            for (int i = 0; i < jsonResultsArray.length(); i++) {
                JSONObject jsonCurrentElement = jsonResultsArray.getJSONObject(i);

                movie_id = jsonCurrentElement.getInt("id");
                title = jsonCurrentElement.getString("title");
                posterPath = jsonCurrentElement.getString("poster_path");
                overview = jsonCurrentElement.getString("overview");
                vote_average = jsonCurrentElement.getDouble("vote_average");
                popularity = jsonCurrentElement.getDouble("popularity");
                release_date = jsonCurrentElement.getString("release_date");

                movieDetails.add(new MovieDetail(movie_id, title, posterPath,
                        overview, vote_average, popularity, release_date));
            }
        } catch (JSONException | NullPointerException exc) {
            exc.printStackTrace();
        }
    }

    public void fetchFromServer(Context context, Uri uri) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                uri.toString(),              // string url
                null,                       // optional JSONObject parameter (?)
                onResponseListener,         //onResponse
                onErrorListener             //onError
        );
        requestQueue.add(jsonObjectRequest);
    }
}
