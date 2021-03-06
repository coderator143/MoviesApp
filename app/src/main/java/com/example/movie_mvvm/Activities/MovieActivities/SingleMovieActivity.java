package com.example.movie_mvvm.Activities.MovieActivities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.movie_mvvm.Activities.HomeActivity;
import com.example.movie_mvvm.Adapters.MovieAdapters.GenreListAdapter;
import com.example.movie_mvvm.NetworkServices.TheMovieDBClient;
import com.example.movie_mvvm.NetworkServices.APIService;
import com.example.movie_mvvm.Entities.Movies.MovieDetails;
import com.example.movie_mvvm.R;
import com.example.movie_mvvm.Adapters.CastListAdapter;
import com.example.movie_mvvm.Repositories.DetailsRepository;
import com.example.movie_mvvm.Utilities.UtilityMethods;
import com.example.movie_mvvm.ViewModels.MoviesViewModel.GenreViewModel;
import com.example.movie_mvvm.ViewModels.MoviesViewModel.SingleMovieViewModel;
import com.example.movie_mvvm.Utilities.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SingleMovieActivity extends AppCompatActivity {

    private DetailsRepository movieDetailsRepository;
    TextView movie_title, movie_tagline, movie_rating, movie_overview, movie_runtime;
    ImageView movie_poster, cancel_item;
    FloatingActionButton home;
    RecyclerView cast_rv, rv_genre;
    private String from;

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
        rv_genre=findViewById(R.id.rv_genre);
        cancel_item=findViewById(R.id.cancel_movie_details);

        int movieId=getIntent().getIntExtra("id", 1);

        from = getIntent().getStringExtra("from");

        CastListAdapter castListAdapter=new CastListAdapter(this, movieId, from);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);

        GenreListAdapter genreListAdapter=new GenreListAdapter(this, movieId);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this, 3);

        APIService apiService= new TheMovieDBClient().getClient();
        movieDetailsRepository = new DetailsRepository(apiService);
        SingleMovieViewModel viewModel = getViewModel(movieId);
        GenreViewModel genreViewModel=getGenreViewModel(movieId);

        genreViewModel.genreList.observe(this, genreListAdapter::submitList);

        viewModel.movieCast.observe(this, castListAdapter::submitList);

        viewModel.moviedetails.observe(this, this::bindUI);

        cast_rv.setLayoutManager(layoutManager);
        cast_rv.setAdapter(castListAdapter);

        rv_genre.setLayoutManager(gridLayoutManager);
        rv_genre.setAdapter(genreListAdapter);
    }

    private GenreViewModel getGenreViewModel(int movieID) {
        return new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new GenreViewModel(movieDetailsRepository, movieID);
            }
        }).get(GenreViewModel.class);
    }

    @Override
    public void onBackPressed() { gotoParentActivity(); }

    @SuppressLint("SetTextI18n")
    private void bindUI(MovieDetails movieDetails) {
        movie_title.setText(movieDetails.get_movie_title());
        if(!movieDetails.get_movie_tagline().equals("")) movie_tagline.setText(movieDetails.get_movie_tagline());
        else movie_tagline.setText("No tagline here, crew lazy af");
        String rating = String.valueOf(movieDetails.get_movie_rating());
        if(!rating.equals("0.0")) movie_rating.setText(rating);
        else movie_rating.setText("Dunno");
        if(!movieDetails.get_movie_overview().equals("")) movie_overview.setText(movieDetails.get_movie_overview());
        else movie_overview.setText("Do watch it. It definitely took more than a day of hard work.");
        String min=movieDetails.get_movie_runtime();
        if(min != null) {
            UtilityMethods ut=new UtilityMethods();
            if(ut.getMinutesFromRuntime(min).equals("0"))
                movie_runtime.setText(ut.getHoursFromRuntime(min)+"h");
            else if(ut.getHoursFromRuntime(min).equals("0")) movie_runtime.setText(ut.getMinutesFromRuntime(min)+"m");
            else movie_runtime.setText(ut.getHoursFromRuntime(min)+"h"+ut.getMinutesFromRuntime(min)+"m");
        }
        else movie_runtime.setText("Dunno");

        String moviePosterURL = Constants.POSTER_BASE_URL + movieDetails.get_movie_poster_path();
        if(movieDetails.get_movie_poster_path()!=null) Glide.with(this)
                .load(moviePosterURL)
                .into(movie_poster);
        else movie_poster.setImageResource(R.drawable.thinking);

        home.setOnClickListener(v -> gotoParentActivity());
        cancel_item.setOnClickListener(v -> gotoParentActivity());
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

    private void gotoParentActivity() {
        if(from != null) {
            if(from.equals("home")) startActivity(new Intent(this, HomeActivity.class));
            else startActivity(new Intent(this, PopularMoviesActivity.class));
            finish();
        }
        else Toast.makeText(this, "from == null", Toast.LENGTH_SHORT).show();
    }
}