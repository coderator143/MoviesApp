package com.example.movie_mvvm.Activities.MovieActivities;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movie_mvvm.Adapters.PopularMoviePagedListAdapter;
import com.example.movie_mvvm.NetworkServices.APIService;
import com.example.movie_mvvm.NetworkServices.TheMovieDBClient;
import com.example.movie_mvvm.R;
import com.example.movie_mvvm.Repositories.PagedListRepository;
import com.example.movie_mvvm.ViewModels.MoviesViewModel.SearchMoviesViewModel;

import java.util.Objects;

public class SearchMoviesActivity extends AppCompatActivity {

    private SearchMoviesViewModel searchMoviesViewModel;
    private PagedListRepository moviePagedListRepository;
    private PopularMoviePagedListAdapter movieAdapter;
    public String query;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movies);

        Toolbar toolbar = findViewById(R.id.toolbar_movies_search);
        setSupportActionBar(toolbar);

        query=getIntent().getStringExtra("query");

        APIService apiService=new TheMovieDBClient().getClient();
        moviePagedListRepository=new PagedListRepository(apiService);
        searchMoviesViewModel=getViewModel();
        movieAdapter=new PopularMoviePagedListAdapter(SearchMoviesActivity.this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(SearchMoviesActivity.this, 3);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = movieAdapter.getItemViewType(position);
                if(viewType == movieAdapter.MOVIE_VIEW_TYPE) return 1;
                else return 3;
            }
        });

        create_movies_list();

        //TextView results = findViewById(R.id.tv_results);

        RecyclerView rv_movie_list = findViewById(R.id.rv_search_movies);
        rv_movie_list.setLayoutManager(gridLayoutManager);
        rv_movie_list.setHasFixedSize(true);
        rv_movie_list.setAdapter(movieAdapter);

        SearchView searchView = findViewById(R.id.sv_movies_search);
        TextView t=findViewById(R.id.tv_movies_search);
        searchView.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if(searchView.isIconified()) {
                t.setText("Results for "+query);
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
            public boolean onQueryTextSubmit(String query1) {
                query=query1;
                searchMoviesViewModel=new SearchMoviesViewModel(moviePagedListRepository, query);
                create_movies_list();
//                searchMoviesViewModel.totalResults.observe(SearchMoviesActivity.this, results::setText);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) { return true; }
        });
    }

    private SearchMoviesViewModel getViewModel() {
        return new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new SearchMoviesViewModel(moviePagedListRepository, query);
            }
        }).get(SearchMoviesViewModel.class);
    }

    private void create_movies_list() {
        searchMoviesViewModel.searchMoviePagedList.observe(SearchMoviesActivity.this,
                searched_movies -> movieAdapter.submitList(searched_movies));
    }
}