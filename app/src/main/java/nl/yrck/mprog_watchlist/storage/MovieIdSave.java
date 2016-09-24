package nl.yrck.mprog_watchlist.storage;

import android.content.Context;

import java.util.List;
import java.util.Set;

import nl.yrck.mprog_watchlist.api.Movie;

public interface MovieIdSave {
    void saveMovieId(String imdbId);
    Set<String> getMovieIds();
}
