package com.example.movie_mvvm.Adapters.MovieAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movie_mvvm.Entities.Movies.Genres;
import com.example.movie_mvvm.R;
import com.example.movie_mvvm.Utilities.UtilityMethods;

public class GenreListAdapter extends ListAdapter<Genres, GenreListAdapter.GenreListHolder> {

    public Context context;
    private int movieID;

    public GenreListAdapter(Context context, int movieID) {
        super(DIFF_CALLBACK);
        this.context=context;
        this.movieID=movieID;
    }

    private static final DiffUtil.ItemCallback<Genres>DIFF_CALLBACK=
            new DiffUtil.ItemCallback<Genres>() {
                @Override
                public boolean areItemsTheSame(@NonNull Genres oldItem, @NonNull Genres newItem) {
                    return oldItem.getGenreID()==newItem.getGenreID();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Genres oldItem, @NonNull Genres newItem) {
                    return oldItem.equals(newItem);
                }
            };

    @NonNull
    @Override
    public GenreListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_list_item, parent, false);
        return new GenreListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreListHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class GenreListHolder extends RecyclerView.ViewHolder {

        private TextView genre;
        private CardView card_genre;

        GenreListHolder(@NonNull View itemView) {
            super(itemView);
            genre=itemView.findViewById(R.id.tv_genre);
            card_genre=itemView.findViewById(R.id.cv_genre);
        }

        @SuppressLint("SetTextI18n")
        void bind(Genres genres) {
            if(genres.getGenreName()!=null) genre.setText(genres.getGenreName());
            else genre.setText("??");

            UtilityMethods ut=new UtilityMethods();
            card_genre.setCardBackgroundColor(itemView.getContext().getResources().getColor(ut.getRandomColor()));
        }
    }
}

