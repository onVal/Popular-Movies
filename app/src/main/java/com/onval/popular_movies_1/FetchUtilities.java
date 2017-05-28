package com.onval.popular_movies_1;

import android.content.Context;
import android.net.Uri;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by gval on 29/05/2017.
 */

public class FetchUtilities {
    final static private String BASE_URL ="https://api.themoviedb.org/3/discover/movie";
    final static private String API_KEY_PARAM = "api_key";
    final static private String PAGE_NUM_PARAM = "page";
    final static private int DEFAULT_NUMBER_OF_PAGES = 2;

    private static Uri createUri(int pageNumber) {

        return Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIEDB_API_KEY)
                .appendQueryParameter(PAGE_NUM_PARAM, String.valueOf(pageNumber))
                .build();
    }

    private static void fetchMovies(Context context) {
        // Use volley library to fetch movie data in JSON format
        createUri(1);

        //TODO: create a request with volley and url
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        //TODO: I actually need to cycle this two times to create two requests
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                createUri(1).toString(),                    // url
                null,                                       // optional JSONObject parameter
                new Response.Listener<JSONObject>() {       // when finishes
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO: To implement
                    }
                },
                new Response.ErrorListener() {              // when error
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO: To implement
                    }
                }

        )

        //TODO: update ui
//        JsonObjectRequest jsonRequest = new JsonObjectRequest(

    }
}
