package nl.yrck.mprog_watchlist;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        recyclerView.setAdapter(recyclerAdapter);
        return rootView;
    }

    public void refreshData(List<Movie> newMovies) {
        movies.clear();
        movies.addAll(newMovies);
        recyclerAdapter.notifyDataSetChanged();
    }
}
