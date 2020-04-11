package com.example.movie_mvvm.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie_mvvm.Entities.Movies.MovieCast;
import com.example.movie_mvvm.R;
import com.example.movie_mvvm.Utilities.Constants;

public class CastListAdapter extends ListAdapter<MovieCast, CastListAdapter.CastListHolder> {

    private Context context;

    public CastListAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context=context;
    }

    private static final DiffUtil.ItemCallback<MovieCast>DIFF_CALLBACK=
            new DiffUtil.ItemCallback<MovieCast>() {
                @Override
                public boolean areItemsTheSame(@NonNull MovieCast oldItem, @NonNull MovieCast newItem) {
                    return oldItem.getCastId()==newItem.getCastId();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull MovieCast oldItem, @NonNull MovieCast newItem) {
                    return oldItem.equals(newItem);
                }
            };

    @NonNull
    @Override
    public CastListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_list_item, parent, false);
        return new CastListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CastListHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class CastListHolder extends RecyclerView.ViewHolder {

        private TextView castName, castCharacter;
        private ImageView castImage;

        CastListHolder(@NonNull View itemView) {
            super(itemView);
            castName=itemView.findViewById(R.id.cv_tv_cast_name);
            castCharacter=itemView.findViewById(R.id.cv_tv_cast_character);
            castImage=itemView.findViewById(R.id.cv_iv_cast);
        }

        @SuppressLint("SetTextI18n")
        void bind(MovieCast movieCast) {
            castName.setText(movieCast.getCastName());
            if(movieCast.getCastCharacter()!=null) castCharacter.setText(movieCast.getCastCharacter());
            else castCharacter.setText("Character unknown");

            String castPosterUrl=Constants.POSTER_BASE_URL+movieCast.getCastPoster();
            if(movieCast.getCastPoster()==null) castImage.setImageResource(R.drawable.character);
            else Glide.with(itemView.getContext())
                    .load(castPosterUrl)
                    .into(castImage);
        }
    }
}
