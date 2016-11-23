package com.onval.popular_movies_1;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by gval on 22/11/16.
 */

public class FetchPopularMoviesTask extends AsyncTask<Void, Void, ArrayList<MovieDetail>> {
    final private String LOG_KEY = FetchPopularMoviesTask.class.getSimpleName();
    public String sortBy = "popularity.desc";

    @Override
    protected ArrayList<MovieDetail> doInBackground(Void... voids) {
        final String BASE_URL ="https://api.themoviedb.org/3/discover/movie";


        //Building the URL using Uri.Builder
        URL requestUrl;

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter("api_key", BuildConfig.MOVIEDB_API_KEY)
                .appendQueryParameter("sort_by", sortBy)
                .build();

        try {
            requestUrl = new URL(uri.toString());
        } catch (MalformedURLException exc) {
            Log.e(LOG_KEY, "Malformed URL: " + uri.toString());
            return null;
        }

        // Getting the JSON response string from the server
        String JSONString = getJSONStringFromServer(requestUrl);
        if (JSONString == null) return null;

        // Parsing the JSON String
         return createMovieListFromJSON (JSONString);
    }

    @Override
    protected void onPostExecute(ArrayList<MovieDetail> movieDetails) {
        if (!movieDetails.isEmpty()) {
            for (MovieDetail m : movieDetails)
                GridFragment.urls.add(m.getPosterPath());
        }
    }

    private String getJSONStringFromServer(URL url) {
        StringBuilder serverResponse = new StringBuilder();

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String currentLine = "";

            while (currentLine != null) {
                currentLine = in.readLine();
                serverResponse.append(currentLine);
            }
        }
        catch (IOException exc) {
            Log.e(LOG_KEY, "An error has occurred while opening the connection");
            return null;
        }

        Log.d(LOG_KEY, serverResponse.toString());
        return serverResponse.toString();
    }

    private ArrayList<MovieDetail> createMovieListFromJSON(String jsonString) {
        ArrayList<MovieDetail> movieDetails = new ArrayList<>();

        String posterPath;
//        String overview;
//        double vote_average;
//        String release_date;

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonResultsArray =  jsonObject.getJSONArray("results");

            for (int i = 0; i < 20; i++) {
                posterPath = (String) jsonResultsArray.getJSONObject(i).get("poster_path");
                movieDetails.add(new MovieDetail(posterPath, "", 0.0, ""));
            }
        } catch (JSONException | NullPointerException exc) {
            exc.printStackTrace();
        }

//        for (MovieDetail m: movieDetails)
//            Log.d(LOG_KEY, m.getPosterPath());

        return movieDetails;
    }
}
