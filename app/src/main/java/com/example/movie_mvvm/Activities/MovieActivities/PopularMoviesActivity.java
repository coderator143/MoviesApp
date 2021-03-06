package com.example.movie_mvvm.Activities.MovieActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.TextView;

import com.example.movie_mvvm.Adapters.MovieAdapters.PopularMoviePagedListAdapter;
import com.example.movie_mvvm.NetworkServices.APIService;
import com.example.movie_mvvm.NetworkServices.TheMovieDBClient;
import com.example.movie_mvvm.R;
import com.example.movie_mvvm.Repositories.PagedListRepository;
import com.example.movie_mvvm.ViewModels.MoviesViewModel.PopularMoviesViewModel;
import java.util.Objects;

public class PopularMoviesActivity extends AppCompatActivity {

    Toolbar toolbar;
    private TextView t;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PopularMoviesViewModel viewModel;
    private PagedListRepository movieRepository;
    private PopularMoviePagedListAdapter movieAdapter;
    private androidx.appcompat.widget.SearchView searchView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=findViewById(R.id.toolbar_popular_movies);
        setSupportActionBar(toolbar);

        APIService apiService = new TheMovieDBClient().getClient();
        movieRepository=new PagedListRepository(apiService);
        viewModel = getInitialViewModel();
        movieAdapter=new PopularMoviePagedListAdapter(this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this, 3);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = movieAdapter.getItemViewType(position);
                if(viewType == movieAdapter.MOVIE_VIEW_TYPE) return 1;
                else return 3;
            }
        });

        create_movies_list();

        swipeRefreshLayout=findViewById(R.id.sr_movie_list);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            Objects.requireNonNull(viewModel.moviePagedList.getValue()).getDataSource().invalidate();
            swipeRefreshLayout.setRefreshing(false);
        });

        RecyclerView rv_movie_list = findViewById(R.id.rv_movie_list);
        rv_movie_list.setLayoutManager(gridLayoutManager);
        rv_movie_list.setHasFixedSize(true);
        rv_movie_list.setAdapter(movieAdapter);

        searchView = findViewById(R.id.sv_movies);
        t=findViewById(R.id.tv_popular_movies);
        searchView.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if(searchView.isIconified()) {
                t.setText("Popular movies");
                searchView.setBackgroundColor(getResources().getColor(R.color.toolbarcolor));
                Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.white),
                PorterDuff.Mode.SRC_ATOP);
            }
            else {
                t.setText("");
                searchView.setBackgroundColor(getResources().getColor(R.color.white));
                Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setDisplayShowHomeEnabled(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent=new Intent(PopularMoviesActivity.this, SearchMoviesActivity.class);
                intent.putExtra("query", query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private PopularMoviesViewModel getInitialViewModel() {
        return new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new PopularMoviesViewModel(movieRepository);
            }
        }).get(PopularMoviesViewModel.class);
    }

    private void create_movies_list() { viewModel.moviePagedList.observe(this, movies -> movieAdapter.submitList(movies)); }
}