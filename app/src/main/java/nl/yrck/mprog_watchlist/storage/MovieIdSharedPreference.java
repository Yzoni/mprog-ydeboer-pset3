package nl.yrck.mprog_watchlist.storage;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

public class MovieIdSharedPreference implements MovieIdSave {

    AppCompatActivity activity;
    private final String PREF_KEY = "movie_save";

    public MovieIdSharedPreference(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void saveMovieId(String imdbId) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPref.edit();

        Set<String> set = new HashSet<>(sharedPref.getStringSet(PREF_KEY, new HashSet<>()));
        set.add(imdbId);
        editor.putStringSet("movie_save", set);
        editor.apply();
    }

    public Set<String> getMovieIds() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        Set<String> set = sharedPref.getStringSet(PREF_KEY, new HashSet<>());

        return set;
    }
}
