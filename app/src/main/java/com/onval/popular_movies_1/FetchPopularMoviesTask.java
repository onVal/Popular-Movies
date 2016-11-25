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

        // Parsing the JSON String
        if (JSONString != null)
            return createMovieListFromJSON (JSONString);
        else
            return null;
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

//        Log.d(LOG_KEY, serverResponse.toString());
        return serverResponse.toString();
    }

    // creates the arrayList of movie details from the json data
    // returns null if something goes wrong
    private ArrayList<MovieDetail> createMovieListFromJSON(String jsonString) {
        ArrayList<MovieDetail> movieDetails = new ArrayList<>();
        String title;
        String posterPath;
        String overview;
        double vote_average;
        String release_date;

        try {
            JSONArray jsonResultsArray =  new JSONObject(jsonString).getJSONArray("results");

            for (int i = 0; i < jsonResultsArray.length(); i++) {
                JSONObject jsonCurrentElement = jsonResultsArray.getJSONObject(i);

                title = (String) jsonCurrentElement.get("title");
                posterPath = (String) jsonCurrentElement.get("poster_path");
                overview = (String) jsonCurrentElement.get("overview");
                vote_average = (double) jsonCurrentElement.get("vote_average");
                release_date = (String) jsonCurrentElement.get("release_date");

                movieDetails.add(new MovieDetail(title, posterPath, overview, vote_average, release_date));
            }
        } catch (JSONException | NullPointerException exc) {
            exc.printStackTrace();
            return null;
        }
        return movieDetails;
    }
}
