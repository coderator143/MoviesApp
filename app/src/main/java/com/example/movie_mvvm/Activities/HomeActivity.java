package com.example.movie_mvvm.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movie_mvvm.Activities.MovieActivities.PopularMoviesActivity;
import com.example.movie_mvvm.Activities.TVShowActivities.PopularTvShowsActivity;
import com.example.movie_mvvm.Adapters.PopularMoviesAdapter;
import com.example.movie_mvvm.NetworkServices.APIService;
import com.example.movie_mvvm.NetworkServices.TheMovieDBClient;
import com.example.movie_mvvm.R;
import com.example.movie_mvvm.Repositories.MovieDetailsRepository;
import com.example.movie_mvvm.ViewModels.MoviesViewModel.MovieHomepageViewModel;

public class HomeActivity extends AppCompatActivity {

    private MovieHomepageViewModel movieHomepageViewModel;
    private MovieDetailsRepository movieRepository;
    private PopularMoviesAdapter movieAdapter;
    private SearchView searchView;
    private TextView t;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);

        APIService apiService = new TheMovieDBClient().getClient();
        movieRepository=new MovieDetailsRepository(apiService);
        movieHomepageViewModel=getPopularMoviesViewModel();
        movieAdapter=new PopularMoviesAdapter(this);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);

        create_popular_movies_list();

        searchView = findViewById(R.id.sv_home);
        t=findViewById(R.id.tv_home);
        searchView.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if(searchView.isIconified()) {
                t.setText("Home");
                searchView.setBackgroundColor(getResources().getColor(R.color.toolbarcolor));
            }
            else {
                t.setText("");
                searchView.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });

        RecyclerView rv_movie_list = findViewById(R.id.rv_list_twenty_popular);
        rv_movie_list.setLayoutManager(layoutManager);
        rv_movie_list.setAdapter(movieAdapter);

        findViewById(R.id.tv_list_twenty_popular_movies).setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, PopularMoviesActivity.class)));

        findViewById(R.id.tv_list_twenty_popular_tv_shows).setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, PopularTvShowsActivity.class)));
    }

    @NonNull
    private MovieHomepageViewModel getPopularMoviesViewModel() {
        return new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new MovieHomepageViewModel(movieRepository);
            }
        }).get(MovieHomepageViewModel.class);
    }

    private void create_popular_movies_list() {
        movieHomepageViewModel.popularMovies.observe(this, movies -> movieAdapter.submitList(movies));
    }
}
