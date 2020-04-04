package com.example.movie_mvvm.UI.PopularMovie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil.ItemCallback;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie_mvvm.Data.Repository.NetworkState;
import com.example.movie_mvvm.Data.VO.Movies.Movie;
import com.example.movie_mvvm.R;
import com.example.movie_mvvm.UI.Single_Movie_Details.SingleMovie;
import com.example.movie_mvvm.Utilities.Constants;

import java.util.Objects;

public class PopularMoviePagedListAdapter extends PagedListAdapter<Movie, RecyclerView.ViewHolder> {

    public int MOVIE_VIEW_TYPE=1;
    private int NETWORK_VIEW_TYPE=2;
    private Context context;
    private NetworkState networkState=null;

    public PopularMoviePagedListAdapter(Context context) {
        super(MOVIE_DIFF_CALLBACK);
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view;
        if(viewType == MOVIE_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.movie_list_item, parent, false);
            return new MovieItemViewHolder(view);
        }
        else {
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false);
            return new NetorkStateViewItemHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==MOVIE_VIEW_TYPE) {
            ((MovieItemViewHolder) holder).bind(Objects.requireNonNull(getItem(position)), context);
        }
        else {
            ((NetorkStateViewItemHolder) holder).bind(networkState);
        }
    }

    private boolean hasExtraRow() {
        return networkState!=null && networkState!=NetworkState.Companion.LOADED;
    }

    @Override
    public int getItemViewType(int position) {
        if(hasExtraRow() && position==getItemCount() - 1) return NETWORK_VIEW_TYPE;
        else return MOVIE_VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        int extraRow=0;
        if(hasExtraRow()) extraRow=1;
        return super.getItemCount() + extraRow;
    }

    private static final ItemCallback<Movie> MOVIE_DIFF_CALLBACK=
        new ItemCallback<Movie>() {
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

    static class MovieItemViewHolder extends RecyclerView.ViewHolder {

        TextView movie_title, movie_release_date;
        ImageView movie_poster;

        MovieItemViewHolder(@NonNull View itemView) {
            super(itemView);
            movie_title=itemView.findViewById(R.id.cv_movie_title);
            movie_release_date=itemView.findViewById(R.id.cv_movie_release_date);
            movie_poster=itemView.findViewById(R.id.cv_iv_image_view);
        }

        void bind(Movie movie, Context context) {
            movie_title.setText(movie.get_title());
            movie_release_date.setText(movie.get_release_date());

            String moviePosterURL= Constants.POSTER_BASE_URL +movie.get_poster_path();
            Glide.with(itemView.getContext())
                    .load(moviePosterURL)
                    .into(movie_poster);

            itemView.setOnClickListener(v -> {
                Intent intent=new Intent(context, SingleMovie.class);
                intent.putExtra("id", movie.get_id());
                context.startActivity(intent);
            });
        }
    }

    static class NetorkStateViewItemHolder extends RecyclerView.ViewHolder {

        TextView error_message;
        ProgressBar progressBar;

        NetorkStateViewItemHolder(@NonNull View itemView) {
            super(itemView);
            error_message=itemView.findViewById(R.id.error_msg_item);
            progressBar=itemView.findViewById(R.id.progress_bar_item);
        }

        void bind(NetworkState networkState) {
            if(networkState!=null && networkState==NetworkState.Companion.LOADING) progressBar.setVisibility(View.VISIBLE);
            else progressBar.setVisibility(View.GONE);

            if(networkState!=null && networkState==NetworkState.Companion.ERROR) {
                error_message.setVisibility(View.VISIBLE);
                error_message.setText(networkState.msg);
            }
            else if(networkState!=null && networkState==NetworkState.Companion.ENDOFLIST) {
                error_message.setVisibility(View.VISIBLE);
                error_message.setText(networkState.msg);
            }
            else error_message.setVisibility(View.GONE);
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState=this.networkState;
        boolean hadExtraRow=hasExtraRow();
        this.networkState=newNetworkState;
        boolean hasExtraRow=hasExtraRow();

        if(hadExtraRow != hasExtraRow) {
            if(hadExtraRow) notifyItemRemoved(super.getItemCount());
            else notifyItemInserted(super.getItemCount());
        }
        else if(hasExtraRow && previousState != newNetworkState) notifyItemChanged(getItemCount()-1);
    }
}
