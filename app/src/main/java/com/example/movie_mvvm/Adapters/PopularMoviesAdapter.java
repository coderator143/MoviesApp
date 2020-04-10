package com.example.movie_mvvm.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie_mvvm.Activities.Movies.SingleMovie;
import com.example.movie_mvvm.Entities.Movie;
import com.example.movie_mvvm.R;
import com.example.movie_mvvm.Utilities.Constants;

public class PopularMoviesAdapter extends ListAdapter<Movie, PopularMoviesAdapter.PopularMoviesListHolder> {

    private Context context;

    public PopularMoviesAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context=context;
    }

    private static final DiffUtil.ItemCallback<Movie>DIFF_CALLBACK=
            new DiffUtil.ItemCallback<Movie>() {
                @Override
                public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
                    return oldItem.get_id()==newItem.get_id();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
                    return oldItem.equals(newItem);
                }
            };

    @NonNull
    @Override
    public PopularMoviesListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_movies_list_item, parent, false);
        return new PopularMoviesListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularMoviesListHolder holder, int position) {
        holder.bind(getItem(position), context);
    }

    static class PopularMoviesListHolder extends RecyclerView.ViewHolder {

        ImageView movie_poster;

        PopularMoviesListHolder(@NonNull View itemView) {
            super(itemView);
            movie_poster = itemView.findViewById(R.id.cv_iv_popular_movies);
        }

        void bind(Movie movie, Context context) {
            String moviePosterURL = Constants.POSTER_BASE_URL + movie.get_poster_path();
            if(movie.get_poster_path()==null) movie_poster.setImageResource(R.drawable.no_movie);
            else Glide.with(itemView.getContext())
                    .load(moviePosterURL)
                    .into(movie_poster);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, SingleMovie.class);
                intent.putExtra("id", movie.get_id());
                context.startActivity(intent);
            });
        }
    }
}
