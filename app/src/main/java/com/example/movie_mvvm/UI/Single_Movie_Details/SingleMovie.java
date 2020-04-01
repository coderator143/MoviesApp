package com.example.movie_mvvm.UI.Single_Movie_Details;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.movie_mvvm.Data.API.TheMovieDBClient;
import com.example.movie_mvvm.Data.API.APIService;
import com.example.movie_mvvm.Data.Repository.NetworkState;
import com.example.movie_mvvm.Data.VO.Movies.MovieDetails;
import com.example.movie_mvvm.R;
import com.example.movie_mvvm.Utilities.Constants;

import java.text.NumberFormat;
import java.util.Locale;

public class SingleMovie extends AppCompatActivity {

    private SingleMovieViewModel viewModel;
    private MovieDetailsRepository movieDetailsRepository;

    TextView movie_title, movie_tagline, movie_release_date, movie_rating, movie_runtime, movie_overview,
    movie_budget, movie_revenue;
    ImageView movie_poster;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_movie);

        movie_title=findViewById(R.id.movie_title);
        movie_tagline=findViewById(R.id.movie_tagline);
        movie_release_date=findViewById(R.id.movie_release_date);
        movie_rating=findViewById(R.id.movie_rating);
        //movie_runtime=findViewById(R.id.movie_runtime);
        movie_overview=findViewById(R.id.movie_overview);
        movie_budget=findViewById(R.id.movie_budget);
        movie_revenue=findViewById(R.id.movie_revenue);
        movie_poster=findViewById(R.id.iv_movie_poster);
        progressBar=findViewById(R.id.progress_bar);

        //int movieId=getParentActivityIntent().getIntExtra("id",1);
        int movieId=getIntent().getIntExtra("id", 1);

        APIService apiService= new TheMovieDBClient().getClient();
        movieDetailsRepository = new MovieDetailsRepository(apiService);
        viewModel=getViewModel(movieId);

        viewModel.moviedetails.observe(this, new Observer<MovieDetails>() {
            @Override
            public void onChanged(MovieDetails movieDetails) {
                bindUI(movieDetails);
            }
        });

        viewModel.networkState.observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {
                if(networkState==NetworkState.Companion.LOADING) progressBar.setVisibility(View.VISIBLE);
                else progressBar.setVisibility(View.GONE);
                if(networkState==NetworkState.Companion.ERROR) progressBar.setVisibility(View.VISIBLE);
                else progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void bindUI(MovieDetails movieDetails) {
        movie_title.setText(movieDetails.get_movie_title());
        movie_tagline.setText(movieDetails.get_movie_tagline());
        movie_release_date.setText(movieDetails.get_movie_release_date());
        String rating = String.valueOf(movieDetails.get_movie_rating());
        movie_rating.setText(rating);
        movie_overview.setText(movieDetails.get_movie_overview());

        NumberFormat formatCurrency=NumberFormat.getCurrencyInstance(Locale.US);
        movie_budget.setText(formatCurrency.format(movieDetails.get_movie_budget()));
        movie_revenue.setText(formatCurrency.format(movieDetails.get_movie_revenue()));

        String moviePosterURL = Constants.POSTER_BASE_URL + movieDetails.get_movie_poster_path();
        Glide.with(this)
                .load(moviePosterURL)
                .into(movie_poster);
    }

    private SingleMovieViewModel getViewModel(final int movieId) {
        return new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new SingleMovieViewModel(movieDetailsRepository, movieId);
            }
        }).get(SingleMovieViewModel.class);
    }
}
