package com.onval.popular_movies_1;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

/**
 * Created by gval on 26/11/16.
 */

public class MyPreferenceActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MyPreferenceFragment()).commit();
//        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_sort_key)));
    }

    public static class MyPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.my_preferences);
        }
    }

//    private void bindPreferenceSummaryToValue(Preference preference) {
//        // Set the listener to watch for value changes.
//        preference.setOnPreferenceChangeListener(this);
//
//        // Trigger the listener immediately with the preference's
//        // current value.
//        onPreferenceChange(preference,
//                PreferenceManager
//                        .getDefaultSharedPreferences(preference.getContext())
//                        .getString(preference.getKey(), ""));
//    }

//    @Override
//    public boolean onPreferenceChange(Preference preference, Object value) {
//        String stringValue = value.toString();
//
//        if (preference instanceof ListPreference) {
//            // For list preferences, look up the correct display value in
//            // the preference's 'entries' list (since they have separate labels/values).
//            ListPreference listPreference = (ListPreference) preference;
//            int prefIndex = listPreference.findIndexOfValue(stringValue);
//            if (prefIndex >= 0) {
//                preference.setSummary(listPreference.getEntries()[prefIndex]);
//            }
//        } else {
//            // For other preferences, set the summary to the value's simple string representation.
//            preference.setSummary(stringValue);
//        }
//        return true;
//    }
}
