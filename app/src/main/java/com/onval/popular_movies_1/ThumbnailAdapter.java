package com.onval.popular_movies_1;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
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
    private final String LOG_TAG = ThumbnailAdapter.class.getSimpleName();

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

        final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p";
        final String IMAGE_SIZE = "w342";

        Uri uri = Uri.parse(BASE_IMAGE_URL).buildUpon()
                .appendPath(IMAGE_SIZE)
                .appendEncodedPath(getItem(position).getPosterPath())
                .build();

        Picasso.with(getContext())
                .load(uri.toString())
                .resize(360, 480)
                .centerCrop()
                .into(imageView);

        //try imageView.setScaleType(CENTER_CROP) (from picasso samples)

        imageView.setBackgroundColor(Color.parseColor("#ff0000"));
        return imageView;
    }
}
