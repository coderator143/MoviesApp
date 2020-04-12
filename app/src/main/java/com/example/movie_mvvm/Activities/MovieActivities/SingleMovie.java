package com.example.movie_mvvm.Activities.MovieActivities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.movie_mvvm.NetworkServices.TheMovieDBClient;
import com.example.movie_mvvm.NetworkServices.APIService;
import com.example.movie_mvvm.Entities.Movies.MovieDetails;
import com.example.movie_mvvm.R;
import com.example.movie_mvvm.Adapters.CastListAdapter;
import com.example.movie_mvvm.Repositories.MovieDetailsRepository;
import com.example.movie_mvvm.Utilities.UtilityMethods;
import com.example.movie_mvvm.ViewModels.MoviesViewModel.SingleMovieViewModel;
import com.example.movie_mvvm.Utilities.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SingleMovie extends AppCompatActivity {

    private MovieDetailsRepository movieDetailsRepository;
    TextView movie_title, movie_tagline, movie_rating, movie_overview, movie_runtime;
    ImageView movie_poster, cancel_item;
    FloatingActionButton home;
    RecyclerView cast_rv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_movie);

        movie_title=findViewById(R.id.movie_title);
        movie_tagline=findViewById(R.id.movie_tagline);
        movie_rating=findViewById(R.id.movie_rating);
        movie_overview=findViewById(R.id.movie_overview);
        movie_poster=findViewById(R.id.iv_movie_poster);
        movie_runtime=findViewById(R.id.tv_movie_runtime);
        home=findViewById(R.id.fab_home);
        cast_rv=findViewById(R.id.rv_cast_list);
        cancel_item=findViewById(R.id.cancel_movie_details);

        CastListAdapter castListAdapter=new CastListAdapter(this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);

        int movieId=getIntent().getIntExtra("id", 1);

        APIService apiService= new TheMovieDBClient().getClient();
        movieDetailsRepository = new MovieDetailsRepository(apiService);
        SingleMovieViewModel viewModel = getViewModel(movieId);

        viewModel.moviedetails.observe(this, this::bindUI);

        viewModel.movieCast.observe(this, castListAdapter::submitList);

        cast_rv.setLayoutManager(layoutManager);
        cast_rv.setHasFixedSize(true);
        cast_rv.setAdapter(castListAdapter);
    }

    @SuppressLint("SetTextI18n")
    private void bindUI(MovieDetails movieDetails) {
        movie_title.setText(movieDetails.get_movie_title());
        if(!movieDetails.get_movie_tagline().equals("")) movie_tagline.setText(movieDetails.get_movie_tagline());
        else movie_tagline.setText("No tagline here, crew lazy af");
        String rating = String.valueOf(movieDetails.get_movie_rating());
        if(!rating.equals("0.0")) movie_rating.setText(rating);
        else movie_rating.setText("Dunno");
        movie_overview.setText(movieDetails.get_movie_overview());
        String min=movieDetails.get_movie_runtime();
        if(min != null) {
            UtilityMethods ut=new UtilityMethods();
            movie_runtime.setText(ut.getHoursFromRuntime(min)+"h"+ut.getMinutesFromRuntime(min)+"m");
        }
        else movie_runtime.setText("Dunno");

        String moviePosterURL = Constants.POSTER_BASE_URL + movieDetails.get_movie_poster_path();
        if(movieDetails.get_movie_poster_path()!=null) Glide.with(this)
                .load(moviePosterURL)
                .into(movie_poster);
        else {
            movie_poster.setImageResource(R.drawable.no_movie);
            movie_poster.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        home.setOnClickListener(v -> finish());
        cancel_item.setOnClickListener(v -> finish());
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