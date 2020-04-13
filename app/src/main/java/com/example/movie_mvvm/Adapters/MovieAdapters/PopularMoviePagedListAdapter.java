package com.example.movie_mvvm.Adapters.MovieAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil.ItemCallback;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie_mvvm.Entities.Movies.Movie;
import com.example.movie_mvvm.R;
import com.example.movie_mvvm.Activities.MovieActivities.SingleMovieActivity;
import com.example.movie_mvvm.Utilities.Constants;

import java.util.Objects;

public class PopularMoviePagedListAdapter extends PagedListAdapter<Movie, RecyclerView.ViewHolder> {

    public int MOVIE_VIEW_TYPE = 1;
    private Context context;

    public PopularMoviePagedListAdapter(Context context) {
        super(MOVIE_DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        view = layoutInflater.inflate(R.layout.popular_list_item, parent, false);
        return new MovieItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == MOVIE_VIEW_TYPE) {
            ((MovieItemViewHolder) holder).itemView.setAnimation(AnimationUtils.loadAnimation(context,
                    R.anim.item_animation_fall_down));
            ((MovieItemViewHolder) holder).bind(Objects.requireNonNull(getItem(position)), context);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return MOVIE_VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    private static final ItemCallback<Movie> MOVIE_DIFF_CALLBACK =
            new ItemCallback<Movie>() {
                @Override
                public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
                    return oldItem.get_id() == newItem.get_id();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
                    return false;
                }
            };

    static class MovieItemViewHolder extends RecyclerView.ViewHolder {

        TextView movie_title, movie_release_date;
        ImageView movie_poster;

        MovieItemViewHolder(@NonNull View itemView) {
            super(itemView);
            movie_title = itemView.findViewById(R.id.cv_title);
            movie_release_date = itemView.findViewById(R.id.cv_release_date);
            movie_poster = itemView.findViewById(R.id.cv_iv_image_view);
        }

        void bind(Movie movie, Context context) {
            movie_title.setText(movie.get_title());
            movie_release_date.setText(movie.get_release_date());

            String moviePosterURL = Constants.POSTER_BASE_URL + movie.get_poster_path();
            if(movie.get_poster_path()==null) movie_poster.setImageResource(R.drawable.thinking);
            else Glide.with(itemView.getContext())
                    .load(moviePosterURL)
                    .into(movie_poster);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, SingleMovieActivity.class);
                intent.putExtra("id", movie.get_id());
                context.startActivity(intent);
            });
        }
    }
}
