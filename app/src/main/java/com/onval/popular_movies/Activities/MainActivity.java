package com.onval.popular_movies.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.stetho.Stetho;
import com.onval.popular_movies.GridFragment;
import com.onval.popular_movies.NoInternetFragment;
import com.onval.popular_movies.R;
import com.onval.popular_movies.Utilities.Utilities;

public class MainActivity extends AppCompatActivity {

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

            Stetho.initializeWithDefaults(getApplicationContext());
        }
    }
}
