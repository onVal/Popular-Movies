package com.onval.popular_movies.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.onval.popular_movies.FavItemClickInterface;
import com.onval.popular_movies.Provider.MovieContract;
import com.onval.popular_movies.R;
import com.onval.popular_movies.Utilities.Utilities;
import com.squareup.picasso.Picasso;

/**
 * Created by gval on 26/06/2017.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavPosterHolder> {
    private Context context;
    private Cursor cursor;
    private int imageColumnIndex;
    private FavItemClickInterface mInterface;

    public FavoritesAdapter(Context context, Cursor cursor, FavItemClickInterface mInterface) {
        this.context = context;
        this.cursor = cursor;
        this.cursor.moveToFirst();
        this.mInterface = mInterface;
        imageColumnIndex = cursor.getColumnIndex(MovieContract.Favorites.POSTERPATH_COLUMN);
    }

    @Override
    public FavPosterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView view = (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_image, null);
        return new FavPosterHolder(view);
    }

    @Override
    public void onBindViewHolder(final FavPosterHolder holder, int position) {
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
        return cursor.getCount();
    }

    // ViewHolder class
    public class FavPosterHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        FavPosterHolder(ImageView imageView) {
            super(imageView);
            this.imageView = imageView;
        }

        private void bind(int position) {
            if (cursor.moveToPosition(position)) {
                Uri imageUri = Utilities.createImageUri((cursor.getString(imageColumnIndex)));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                Picasso.with(context)
                        .load(imageUri)
                        .into(imageView);
            }
        }
    }

    public void updateCursor() {
        cursor = context.getContentResolver().query(MovieContract.Favorites.CONTENT_URI, null, null, null, null);
    }
}