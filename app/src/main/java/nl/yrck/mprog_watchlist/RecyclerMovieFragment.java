package nl.yrck.mprog_watchlist;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nl.yrck.mprog_watchlist.api.Movie;


public class RecyclerMovieFragment extends Fragment {

    static final String TAG = "RECYLCER_MOVIE_FRAGMENT";

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;

    List<Movie> movies;

    public RecyclerMovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        movies = getArguments().getParcelableArrayList("MOVIES");

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new RecyclerAdapter(movies, getContext());
        recyclerAdapter.setOnItemClickListener(new RecyclerAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                startMovieDetailsActivity(v);
            }
        });
        recyclerView.setAdapter(recyclerAdapter);
        return rootView;
    }

    public void refreshData(List<Movie> newMovies) {
        for (Movie movie : newMovies) {
            Log.d("Loading movies", movie.getTitle());
        }

        movies.clear();
        movies.addAll(newMovies);
        recyclerAdapter.notifyDataSetChanged();
    }

    private void startMovieDetailsActivity(View view) {

        TextView movieTitle = (TextView) view.findViewById(R.id.card_title);

        Bundle bundle = new Bundle();
        bundle.putString("IMDB_ID", view.getTag().toString());
        bundle.putString("MOVIE_TITLE", movieTitle.getText().toString());
        Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
