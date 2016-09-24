package nl.yrck.mprog_watchlist.api;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieSearchResult {
    private List<Movie> movies;
    private int totalResults;

    @JsonCreator
    MovieSearchResult(
            @JsonProperty("Search") List<Movie> movies,
            @JsonProperty("totalResults") int totalResults) {
        this.movies = movies;
        this.totalResults = totalResults;
    }

//    public MovieSearchResult(List<Movie> movies, int totalResults) {
//        this.movies = movies;
//        this.totalResults = totalResults;
//    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
