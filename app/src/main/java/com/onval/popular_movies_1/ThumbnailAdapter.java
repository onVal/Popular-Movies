package com.onval.popular_movies_1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.onval.popular_movies_1.Utilities.FetchUtilities;
import com.squareup.picasso.Picasso;

/**
 * Created by gval on 21/11/16.
 */

public class ThumbnailAdapter extends ArrayAdapter<MovieDetail> {
    private final String LOG_TAG = ThumbnailAdapter.class.getSimpleName();

    ThumbnailAdapter(Context context) {
        super (context, 0, GridFragment.movieDetails);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = (ImageView) convertView;
        if (imageView == null) {
            imageView = new ImageView(getContext());
        }

        String url = FetchUtilities.extractImageUri(getItem(position)).toString();

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        Picasso.with(getContext())
                .load(url)
                .into(imageView);

        return imageView;
    }
}
