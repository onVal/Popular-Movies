package com.onval.popular_movies_1;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by gval on 21/11/16.
 */

public class ThumbnailAdapter extends ArrayAdapter<MovieDetail> {

    ThumbnailAdapter(Context context, ArrayList<MovieDetail> movieDetails) {
        super (context, 0, movieDetails);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = (ImageView) convertView;
        if (imageView == null) {
            imageView = new ImageView(getContext());
        }

        String url = "http://image.tmdb.org/t/p/" + "w342" + getItem(position).getPosterPath();
        Log.d("URL", url);

        Picasso.with(getContext())
                .load(url)
                .resize(360, 480)
                .centerCrop()
                .into(imageView);

        //try imageView.setScaleType(CENTER_CROP) (from picasso samples)

        imageView.setBackgroundColor(Color.parseColor("#ff0000"));
        return imageView;
    }
}
