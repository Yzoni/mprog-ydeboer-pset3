package nl.yrck.mprog_watchlist;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import nl.yrck.mprog_watchlist.api.Movie;
import nl.yrck.mprog_watchlist.loaders.MoviesLoader;
import nl.yrck.mprog_watchlist.storage.MovieIdSave;
import nl.yrck.mprog_watchlist.storage.MovieIdSharedPreference;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener((View view) -> startSearchActivity());


        initFragment();

        getMovies();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void initFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RecyclerMovieFragment recyclerMovieFragment = new RecyclerMovieFragment();

        ArrayList<Movie> movies = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("MOVIES", movies);
        bundle.putBoolean("SHOW_PROGRESSBAR", true);
        recyclerMovieFragment.setArguments(bundle);

        fragmentTransaction.add(R.id.fragment, recyclerMovieFragment, RecyclerMovieFragment.TAG);
        fragmentTransaction.commit();
    }

    private void getMovies() {
        MovieIdSave movieIdSave = new MovieIdSharedPreference(this);
        Set<String> set = movieIdSave.getMovieIds();
        String[] movie_ids = set.toArray(new String[set.size()]);

        Bundle bundle = new Bundle();
        bundle.putStringArray("MOVIE_ID_S", movie_ids);

        getSupportLoaderManager().restartLoader(0, bundle, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("By Yorick de Boer")
                    .setTitle("About")
                    .setCancelable(true)
                    .setNeutralButton("Dismiss", (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.dismiss();
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void startSearchActivity() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        String[] movie_id_s = args.getStringArray("MOVIE_ID_S");
        return new MoviesLoader(this, movie_id_s);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        RecyclerMovieFragment recyclerMovieFragment = (RecyclerMovieFragment) getFragmentManager()
                .findFragmentByTag(RecyclerMovieFragment.TAG);
        recyclerMovieFragment.hideProgressbar();
        recyclerMovieFragment.refreshData(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        Log.d("searchactivty", "loader reset");
    }
}
