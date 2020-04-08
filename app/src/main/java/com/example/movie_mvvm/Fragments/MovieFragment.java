package com.example.movie_mvvm.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.movie_mvvm.Adapters.PopularMoviePagedListAdapter;
import com.example.movie_mvvm.DataSource.MovieDataSourceFactory;
import com.example.movie_mvvm.Entities.Movie;
import com.example.movie_mvvm.NetworkServices.APIService;
import com.example.movie_mvvm.NetworkServices.TheMovieDBClient;
import com.example.movie_mvvm.R;
import com.example.movie_mvvm.Utilities.Constants;
import com.example.movie_mvvm.Utilities.MyApplication;
import com.example.movie_mvvm.ViewModels.MovieFragmentViewModel;
import com.example.movie_mvvm.Repositories.MoviePagedListRepository;

import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;


public class MovieFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private MovieFragmentViewModel viewModel;
    private MoviePagedListRepository movieRepository;
    private PopularMoviePagedListAdapter movieAdapter;
    APIService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_movie, container, false);

        apiService = new TheMovieDBClient().getClient();
        movieRepository=new MoviePagedListRepository(apiService);
        viewModel = getInitialViewModel();
        movieAdapter=new PopularMoviePagedListAdapter(getContext());
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(), 3);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = movieAdapter.getItemViewType(position);
                if(viewType == movieAdapter.MOVIE_VIEW_TYPE) return 1;
                else return 3;
            }
        });

        create_movies_list();

        swipeRefreshLayout=v.findViewById(R.id.sr_movie_list);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            Objects.requireNonNull(viewModel.moviePagedList.getValue()).getDataSource().invalidate();

            swipeRefreshLayout.setRefreshing(false);
        });

        RecyclerView rv_movie_list = v.findViewById(R.id.rv_movie_list);
        rv_movie_list.setLayoutManager(gridLayoutManager);
        rv_movie_list.setHasFixedSize(true);
        rv_movie_list.setAdapter(movieAdapter);

        return v;
    }

    private MovieFragmentViewModel getInitialViewModel() {
        return new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new MovieFragmentViewModel(movieRepository);
            }
        }).get(MovieFragmentViewModel.class);
    }

    private void create_movies_list() {
        viewModel.moviePagedList.observe(getViewLifecycleOwner(), movies -> {
            movieAdapter.submitList(movies);
        });
    }
}
