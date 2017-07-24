package com.onval.popular_movies;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.onval.popular_movies.Utilities.Utilities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by gval on 08/06/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MoviePosterHolder> {
    private Context mContext;
    private ArrayList<MovieDetail> mMovieDetails;
    private ItemClickInterface mInterface;

    RecyclerAdapter(Context context, ArrayList<MovieDetail> movieDetails, ItemClickInterface myInterface) {
        mContext = context;
        mMovieDetails = movieDetails;
        mInterface = myInterface;
    }

    @Override
    public MoviePosterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(R.layout.movie_image, null);

        return new MoviePosterHolder(imageView);
    }

    @Override
    public void onBindViewHolder(final MoviePosterHolder holder, int position) {
        holder.bind(position);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterface.onItemClick(holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieDetails.size();
    }

    // ViewHolder class
    class MoviePosterHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        MoviePosterHolder(ImageView imageView) {
            super(imageView);
            this.imageView = imageView;
        }

        private void bind(int position) {

            Uri imageUri = Utilities.extractImageUri(mMovieDetails.get(position));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            Picasso.with(mContext)
                    .load(imageUri)
                    .into(imageView);

        }
    }
}
