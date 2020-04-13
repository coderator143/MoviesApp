package com.example.movie_mvvm.Adapters.TvShowAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie_mvvm.Activities.MovieActivities.SingleMovieActivity;
import com.example.movie_mvvm.Entities.Movies.Movie;
import com.example.movie_mvvm.Entities.TVShows.TVShow;
import com.example.movie_mvvm.R;
import com.example.movie_mvvm.Utilities.Constants;

public class PopularTvShowAdapter extends ListAdapter<TVShow, PopularTvShowAdapter.PopularTvSHowListHolder> {

    private Context context;

    public PopularTvShowAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context=context;
    }

    private static final DiffUtil.ItemCallback<TVShow>DIFF_CALLBACK=
            new DiffUtil.ItemCallback<TVShow>() {
                @Override
                public boolean areItemsTheSame(@NonNull TVShow oldItem, @NonNull TVShow newItem) {
                    return oldItem.get_tvId()==newItem.get_tvId();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull TVShow oldItem, @NonNull TVShow newItem) {
                    return oldItem.equals(newItem);
                }
            };

    @NonNull
    @Override
    public PopularTvSHowListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_twenty_list_item,
                parent, false);
        return new PopularTvSHowListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularTvSHowListHolder holder, int position) {
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.down_to_up));
        holder.bind(getItem(position), context);
    }

    static class PopularTvSHowListHolder extends RecyclerView.ViewHolder {

        ImageView tvshow_poster;

        PopularTvSHowListHolder(@NonNull View itemView) {
            super(itemView);
            tvshow_poster = itemView.findViewById(R.id.cv_iv_popular);
        }

        void bind(TVShow tvShow, Context context) {
            String moviePosterURL = Constants.POSTER_BASE_URL + tvShow.get_tvPosterPath();
            if(tvShow.get_tvPosterPath()==null) tvshow_poster.setImageResource(R.drawable.no_movie);
            else Glide.with(itemView.getContext())
                    .load(moviePosterURL)
                    .into(tvshow_poster);

            itemView.setOnClickListener(v -> {
                Toast.makeText(context, "Todo", Toast.LENGTH_SHORT).show();
            });
        }
    }
}

