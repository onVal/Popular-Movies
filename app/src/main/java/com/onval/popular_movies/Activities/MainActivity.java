package com.onval.popular_movies.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.stetho.Stetho;
import com.onval.popular_movies.EmptyFavFragment;
import com.onval.popular_movies.FavoritesFragment;
import com.onval.popular_movies.MovieFragment;
import com.onval.popular_movies.NoInternetFragment;
import com.onval.popular_movies.R;
import com.onval.popular_movies.Utilities.Utilities;

public class MainActivity extends AppCompatActivity {
    public static final String MOVIE_FRAGMENT_TAG = "movie_tag";
    public static final String FAV_FRAGMENT_TAG = "fav_tag";
    public static final String NO_FAV_FRAGMENT_TAG = "nofav_tag";
    public static final String NO_INTERNET_TAG = "no_internet";

    FragmentManager fragmentManager;
//    MyPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

//        presenter = new MyPresenter();

        if (savedInstanceState == null) {
            /* Check for internet connection at launch,
             * load either the expected gridFragment, or a temporary
             * NoInternetFragment until the device has internet access again
             */
            if (Utilities.isOnline(getApplicationContext())) {
                fragmentManager.beginTransaction()
                        .add(R.id.main_container, new MovieFragment(), MOVIE_FRAGMENT_TAG)
                        .commit();
            } else {
                fragmentManager.beginTransaction()
                        .add(R.id.main_container, new NoInternetFragment(), NO_INTERNET_TAG)
                        .commit();
            }

            Stetho.initializeWithDefaults(getApplicationContext());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.settings) {
            startActivity(new Intent(this, MyPreferenceActivity.class));
        }

        if (item.getItemId() == R.id.favorites) {
            /* is this bad design? yes!
             * will I do it again? no!
             * will you forgive me for this? hopefully :3
             */
            try {
                if (!fragmentManager.findFragmentByTag(NO_INTERNET_TAG).isVisible()) {
                    toggleFavoriteMenuItemTitle(item);
                    onFavoritesClicked();
                }

            } catch (NullPointerException exc) {
                //if null -> also toggle favorites
                toggleFavoriteMenuItemTitle(item);
                onFavoritesClicked();
                exc.printStackTrace();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void onFavoritesClicked() {

        if (fragmentManager.findFragmentByTag(MOVIE_FRAGMENT_TAG).isVisible()) {

            if (Utilities.isFavoritesEmpty(this)) {
                fragmentManager.beginTransaction()
                        .replace(R.id.main_container, new EmptyFavFragment(), NO_FAV_FRAGMENT_TAG)
                        .addToBackStack(null)
                        .commit();
            }
            else {
                fragmentManager.beginTransaction()
                        .replace(R.id.main_container, new FavoritesFragment(), FAV_FRAGMENT_TAG)
                        .addToBackStack(null)
                        .commit();

            }
        }
        else {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_container, new MovieFragment(), MOVIE_FRAGMENT_TAG)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void toggleFavoriteMenuItemTitle(MenuItem item) {
        if (item.getTitle().equals(getString(R.string.show_favorites)))
            item.setTitle(getString(R.string.show_movies));
        else
            item.setTitle(getString(R.string.show_favorites));
    }
}
