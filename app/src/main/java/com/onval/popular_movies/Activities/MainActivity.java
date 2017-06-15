package com.onval.popular_movies.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.onval.popular_movies.GridFragment;
import com.onval.popular_movies.NoInternetFragment;
import com.onval.popular_movies.R;
import com.onval.popular_movies.Utilities.Utilities;

public class MainActivity extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            /* Check for internet connection at launch,
             * load either the expected gridFragment, or a temporary
             * NoInternetFragment until the device has internet access again
             */
            if (Utilities.isOnline(this)) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.activity_main, new GridFragment())
                        .commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.activity_main, new NoInternetFragment())
                        .commit();
            }
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
            Log.d("lol", "Settings clicked");
            startActivity(new Intent(this, MyPreferenceActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
