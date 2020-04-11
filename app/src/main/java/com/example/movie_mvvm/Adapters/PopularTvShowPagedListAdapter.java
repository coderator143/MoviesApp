package com.example.movie_mvvm.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie_mvvm.Activities.MovieActivities.SingleMovie;
import com.example.movie_mvvm.Entities.TVShows.TVShow;
import com.example.movie_mvvm.R;
import com.example.movie_mvvm.Utilities.Constants;

import java.util.Objects;

public class PopularTvShowPagedListAdapter extends PagedListAdapter<TVShow, RecyclerView.ViewHolder> {

    public int TV_SHOW_VIEW_TYPE = 1;
    private Context context;

    public PopularTvShowPagedListAdapter(Context context) {
        super(TV_SHOW_DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        view = layoutInflater.inflate(R.layout.popular_list_item, parent, false);
        return new TvShowItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TV_SHOW_VIEW_TYPE) {
            ((TvShowItemViewHolder) holder).itemView.setAnimation(AnimationUtils.loadAnimation(context,
                    R.anim.item_animation_fall_down));
            ((TvShowItemViewHolder) holder).bind(Objects.requireNonNull(getItem(position)), context);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return TV_SHOW_VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    private static final DiffUtil.ItemCallback<TVShow> TV_SHOW_DIFF_CALLBACK =
            new DiffUtil.ItemCallback<TVShow>() {
                @Override
                public boolean areItemsTheSame(@NonNull TVShow oldItem, @NonNull TVShow newItem) {
                    return oldItem.get_tvId() == newItem.get_tvId();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull TVShow oldItem, @NonNull TVShow newItem) {
                    return oldItem.equals(newItem);
                }
            };

    static class TvShowItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvshow_title, tvshow_release_date;
        ImageView tvshow_poster;

        TvShowItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvshow_title = itemView.findViewById(R.id.cv_title);
            tvshow_release_date = itemView.findViewById(R.id.cv_release_date);
            tvshow_poster = itemView.findViewById(R.id.cv_iv_image_view);
        }

        void bind(TVShow tvShow, Context context) {
            tvshow_title.setText(tvShow.get_tvShowName());
            tvshow_release_date.setText(tvShow.get_firstAirDate());

            String tvShowPosterURL = Constants.POSTER_BASE_URL + tvShow.get_tvPosterPath();
            if(tvShow.get_tvPosterPath()==null) tvshow_poster.setImageResource(R.drawable.no_movie);
            else Glide.with(itemView.getContext())
                    .load(tvShowPosterURL)
                    .into(tvshow_poster);

            itemView.setOnClickListener(v -> {
//                Intent intent = new Intent(context, SingleMovie.class);
//                intent.putExtra("id", tvShow.get_tvId());
//                context.startActivity(intent);
                Toast.makeText(context, "To do", Toast.LENGTH_SHORT).show();
            });
        }
    }
}

