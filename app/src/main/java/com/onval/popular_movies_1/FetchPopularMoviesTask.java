package com.onval.popular_movies_1;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
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
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by gval on 22/11/16.
 */

public class FetchPopularMoviesTask extends AsyncTask<String, Void, ArrayList<MovieDetail>> {
    final private String LOG_KEY = FetchPopularMoviesTask.class.getSimpleName();
    final private String BASE_URL ="https://api.themoviedb.org/3/discover/movie";
    final private String API_KEY_PARAM = "api_key";
    final private String PAGE_NUM_PARAM = "page";
    final private int NUMBER_OF_PAGES = 2;

    Context context;



    FetchPopularMoviesTask(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<MovieDetail> doInBackground(String... param) {

        ArrayList<MovieDetail> movieDetailArrayList = new ArrayList<>();

        String JSONString;

        /* This loop fetches NUMBER_OF_PAGES times through the moviedb servers
         * fetching multiple 'pages' of movies (each page gives you 20 results)
         * for efficiency and simplicity I decided to fetch just from 2 pages (40 results)
         * since when I go for a higher number, the gridview/adapter start panicking
         */
        for (int i=1; i <= NUMBER_OF_PAGES; i++) {
            //Building the URL using Uri.Builder
            URL requestUrl;

            Uri uri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIEDB_API_KEY)
                    .appendQueryParameter(PAGE_NUM_PARAM, String.valueOf(i))
                    .build();

            Log.d(LOG_KEY, uri.toString());

            try {
                requestUrl = new URL(uri.toString());
            } catch (MalformedURLException exc) {
                Log.e(LOG_KEY, "Malformed URL: " + uri.toString());
                return null;
            }

            // Getting the JSON response string from the server
            JSONString = getJSONStringFromServer(requestUrl);


            // Parsing and returning the JSON String
            if (JSONString != null)
                movieDetailArrayList.addAll(createMovieListFromJSON(JSONString));
            else
                return null;
        }
        return movieDetailArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<MovieDetail> movieDetails) {
        if (!movieDetails.isEmpty()) {
            // Sort the results according to option selected before updating the adapter
            String sortOption = PreferenceManager.getDefaultSharedPreferences(context)
                    .getString(context.getString(R.string.pref_sort_key),
                            context.getString(R.string.pref_popularity_value));

            if (sortOption.equals(context.getString(R.string.pref_ratings_value))) {
                Collections.sort(movieDetails, new Comparator<MovieDetail>() {
                    @Override
                    public int compare(MovieDetail md1, MovieDetail md2) {
                        return ((Double) Math.signum((md2.getVote_average() - md1.getVote_average()))).intValue();
                    }
                });
            }

//            GridFragment.adapter.clear();
//            GridFragment.adapter.addAll(movieDetails);
        }
    }

    //TODO: Use OKHttp, Volley or Gson instead of this
    private String getJSONStringFromServer(URL url) {
        StringBuilder serverResponse = new StringBuilder();

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String currentLine = "";

            while (currentLine != null) {
                serverResponse.append(currentLine);
                currentLine = in.readLine();
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

                title = jsonCurrentElement.getString("title");
                posterPath = jsonCurrentElement.getString("poster_path");
                overview = jsonCurrentElement.getString("overview");
                vote_average = jsonCurrentElement.getDouble("vote_average");
                release_date = jsonCurrentElement.getString("release_date");

                movieDetails.add(new MovieDetail(title, posterPath, overview, vote_average, release_date));
            }
        } catch (JSONException | NullPointerException exc) {
            exc.printStackTrace();
            return null;
        }
        return movieDetails;
    }
}
