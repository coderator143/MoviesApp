package com.example.movie_mvvm.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.movie_mvvm.Data.API.TheMovieDBClient;
import com.example.movie_mvvm.Data.API.APIService;
import com.example.movie_mvvm.Data.Repository.NetworkState;
import com.example.movie_mvvm.Data.VO.Movies.Movie;
import com.example.movie_mvvm.R;
import com.example.movie_mvvm.UI.PopularMovie.MovieFragmentViewModel;
import com.example.movie_mvvm.UI.PopularMovie.MoviePagedListRepository;
import com.example.movie_mvvm.UI.PopularMovie.PopularMoviePagedListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private MovieFragmentViewModel viewModel;
    MoviePagedListRepository movieRepository;
    RecyclerView rv_movie_list;
    TextView error_msg;
    PopularMoviePagedListAdapter movieAdapter;
    ProgressBar prog_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        APIService apiService = new TheMovieDBClient().getClient();
        movieRepository=new MoviePagedListRepository(apiService);
        viewModel=getViewModel();

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
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                create_movies_list();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        error_msg=findViewById(R.id.txt_error_popular);
        prog_bar=findViewById(R.id.progress_bar_popular);

        viewModel.networkState.observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {
                if(viewModel.isListEmpty() && networkState==NetworkState.Companion.LOADING) prog_bar.setVisibility(View.VISIBLE);
                else prog_bar.setVisibility(View.GONE);
                if(viewModel.isListEmpty() && networkState==NetworkState.Companion.ERROR) prog_bar.setVisibility(View.VISIBLE);
                else prog_bar.setVisibility(View.GONE);

                if(!viewModel.isListEmpty()) movieAdapter.setNetworkState(networkState);
            }
        });

        rv_movie_list=findViewById(R.id.rv_movie_list);
        rv_movie_list.setLayoutManager(gridLayoutManager);
        rv_movie_list.setHasFixedSize(true);
        rv_movie_list.setAdapter(movieAdapter);
    }

    private MovieFragmentViewModel getViewModel() {
        return new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new MovieFragmentViewModel(movieRepository);
            }
        }).get(MovieFragmentViewModel.class);
    }

    public void create_movies_list() {
        viewModel.moviePagedList.observe(this, new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> movies) {
                movieAdapter.submitList(movies);
            }
        });
    }
}
