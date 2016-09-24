package nl.yrck.mprog_watchlist;

import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nl.yrck.mprog_watchlist.api.MovieFull;
import nl.yrck.mprog_watchlist.loaders.MovieFullLoader;
import nl.yrck.mprog_watchlist.storage.MovieIdSave;
import nl.yrck.mprog_watchlist.storage.MovieIdSharedPreference;


public class MovieDetailsActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<MovieFull>,
        View.OnClickListener {

    TextView plot;
    TextView year;
    TextView genre;
    ImageView poster;
    Button showPlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        String imdbId = getIntent().getExtras().getString("IMDB_ID");
        String movieTitle = getIntent().getExtras().getString("MOVIE_TITLE");
        toolbar.setTitle(movieTitle);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener((View view) -> {
            saveMovie(imdbId);
            Snackbar.make(view, "Saved movie", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        plot = (TextView) findViewById(R.id.details_plot);
        poster = (ImageView) findViewById(R.id.details_poster);
        year = (TextView) findViewById(R.id.details_year);
        genre = (TextView) findViewById(R.id.details_genre);

        showPlot = (Button) findViewById(R.id.show_plot);
        showPlot.setOnClickListener(this);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            poster.setTransitionName("TRANS");
        }

        getSupportLoaderManager().initLoader(0, getIntent().getExtras(), this);
    }

    private void saveMovie(String imdbId) {
        MovieIdSave movieIdSave = new MovieIdSharedPreference(this);
        movieIdSave.saveMovieId(imdbId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_plot:
                Log.d("ssj", "kskks");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<MovieFull> onCreateLoader(int id, Bundle args) {
        String imdbId = args.getString("IMDB_ID");
        Log.d("Should init loading ", imdbId);
        return new MovieFullLoader(this, imdbId);
    }

    @Override
    public void onLoadFinished(Loader<MovieFull> loader, MovieFull data) {
        plot.setText(data.getPlot().substring(0, 200) + "...");
        Picasso.with(this).load(data.getPoster()).into(poster);
        year.setText(data.getYear());
        genre.setText(data.getGenre());

    }

    @Override
    public void onLoaderReset(Loader<MovieFull> loader) {
        Log.d("searchactivty", "loader reset");
    }

}
