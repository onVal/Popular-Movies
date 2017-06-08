package com.onval.popular_movies;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.onval.popular_movies.Utilities.FetchUtilities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by gval on 08/06/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MoviePosterHolder> {
    private Context mContext;
    private ArrayList<MovieDetail> mMovieDetails;

    RecyclerAdapter(Context context, ArrayList<MovieDetail> movieDetails) {
        mContext = context;
        mMovieDetails = movieDetails;
    }

    @Override
    public MoviePosterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(R.layout.movie_image, null);

        return new MoviePosterHolder(imageView);
    }

    @Override
    public void onBindViewHolder(MoviePosterHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mMovieDetails.size();
    }

    class MoviePosterHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        MoviePosterHolder(ImageView imageView) {
            super(imageView);
            this.imageView = imageView;
        }

        private void bind(int position) {

            Uri imageUri = FetchUtilities.extractImageUri(mMovieDetails.get(position));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            Picasso.with(mContext)
                    .load(imageUri)
                    .into(imageView);

        }
    }
}
