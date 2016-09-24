package nl.yrck.mprog_watchlist;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.ArrayList;

import nl.yrck.mprog_watchlist.api.Movie;
import nl.yrck.mprog_watchlist.api.MovieSearchResult;
import nl.yrck.mprog_watchlist.api.OMDbAPI;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d("test", "test");

        handleIntent(getIntent());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("title", "title", "title", "title", "title"));
        movies.add(new Movie("title", "title", "title", "title", "title"));

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RecyclerMovieFragment recyclerMovieFragment = new RecyclerMovieFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("MOVIES", movies);
        recyclerMovieFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fragment, recyclerMovieFragment, RecyclerMovieFragment.TAG);
        fragmentTransaction.commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Search search = new Search();
            Log.d("Search query", query);
            search.execute(query);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.onActionViewExpanded();
        return true;
    }

    private class Search extends AsyncTask<String, Void, MovieSearchResult> {

        @Override
        protected MovieSearchResult doInBackground(String[] params) {
            OMDbAPI omDbAPI = new OMDbAPI();
            MovieSearchResult movieSearchResult = omDbAPI.Search(params[0], null, null);
            for (Movie movie : movieSearchResult.getMovies()) {
                Log.e("lls", "" + movie.getTitle());
            }
            return movieSearchResult;
        }

        @Override
        protected void onPostExecute(MovieSearchResult movieSearchResult) {
            RecyclerMovieFragment recyclerMovieFragment = (RecyclerMovieFragment) getFragmentManager()
                    .findFragmentByTag(RecyclerMovieFragment.TAG);
            recyclerMovieFragment.refreshData(movieSearchResult.getMovies());

            super.onPostExecute(movieSearchResult);
        }
    }
}
