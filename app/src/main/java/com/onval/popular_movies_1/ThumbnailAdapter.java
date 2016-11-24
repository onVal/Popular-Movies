package com.onval.popular_movies_1;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by gval on 21/11/16.
 */

public class ThumbnailAdapter extends ArrayAdapter<String> {

    ThumbnailAdapter(Context context, List<String> urlStrings) {
        super (context, 0, urlStrings);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = (ImageView) convertView;
        if (imageView == null) {
            imageView = new ImageView(getContext());
        }

        String url = "http://image.tmdb.org/t/p/" + "w342" + getItem(position);
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
