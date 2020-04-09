package com.example.movie_mvvm.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.movie_mvvm.Adapters.PopularMoviesAdapter;
import com.example.movie_mvvm.NetworkServices.APIService;
import com.example.movie_mvvm.NetworkServices.TheMovieDBClient;
import com.example.movie_mvvm.R;
import com.example.movie_mvvm.Repositories.MovieDetailsRepository;
import com.example.movie_mvvm.ViewModels.MovieHomepageViewModel;

public class HomeActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private MovieHomepageViewModel movieHomepageViewModel;
    private MovieDetailsRepository movieRepository;
    private PopularMoviesAdapter movieAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        APIService apiService = new TheMovieDBClient().getClient();
        movieRepository=new MovieDetailsRepository(apiService);
        movieHomepageViewModel=getPopularMoviesViewModel();
        movieAdapter=new PopularMoviesAdapter(this);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);

        create_popular_movies_list();

        RecyclerView rv_movie_list = findViewById(R.id.rv_list_twenty_popular);
        rv_movie_list.setLayoutManager(layoutManager);
        rv_movie_list.setAdapter(movieAdapter);

        findViewById(R.id.tv_list_twenty_popular_movies).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MoviesActivity.class));
            }
        });
    }

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
