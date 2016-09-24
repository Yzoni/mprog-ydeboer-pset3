package nl.yrck.mprog_watchlist.api;

import android.util.JsonReader;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class OMDbAPI {

    private static String BASE_URL = "http://www.omdbapi.com/";

    public OMDbAPI() {

    }

    public MovieSearchResult Search(String query, String type, String year) {
        try {
            URL url = new URL(buildSearchUrl(query, type, year));
            Log.d("Opening url", url.toString());
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(url, MovieSearchResult.class);
        } catch (MalformedURLException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
        return null;
    }

    public Movie getById() {
        return null;
    }

    public Movie getByTitle() {
        return null;
    }

    private String buildSearchUrl(String query, String type, String year) {
        String url = BASE_URL;

        url += "?s=" + query;

        if (type != null) {
            url += "?type=" + type;
        }

        if (year != null) {
            url += "?y=" + year;
        }

        return url;
    }
}
