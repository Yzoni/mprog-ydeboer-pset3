package nl.yrck.mprog_watchlist;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import nl.yrck.mprog_watchlist.api.Movie;


class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Movie> movies;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView cardPoster;
        TextView cardTitle;
        TextView cardYear;

        ViewHolder(View v) {
            super(v);
            cardPoster = (ImageView) v.findViewById(R.id.card_poster);
            cardTitle = (TextView) v.findViewById(R.id.card_title);
            cardYear = (TextView) v.findViewById(R.id.card_year);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    RecyclerAdapter(List<Movie> myDataset, Context myContext) {
        movies = myDataset;
        context = myContext;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Picasso.with(context).load(movies.get(position).getPoster()).into(holder.cardPoster);

        holder.cardTitle.setText(movies.get(position).getTitle());
        holder.cardYear.setText(movies.get(position).getYear());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return movies.size();
    }
}
