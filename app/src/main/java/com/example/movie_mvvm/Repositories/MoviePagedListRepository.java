package com.example.movie_mvvm.Repositories;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.movie_mvvm.NetworkServices.APIService;
import com.example.movie_mvvm.DataSource.MovieDataSource;
import com.example.movie_mvvm.DataSource.MovieDataSourceFactory;
import com.example.movie_mvvm.Entities.Movie;
import com.example.movie_mvvm.Utilities.Constants;

import io.reactivex.disposables.CompositeDisposable;

public class MoviePagedListRepository {

    private APIService apiService;
    private LiveData<PagedList<Movie>> moviePagedList;
    private MovieDataSourceFactory moviesDataSourceFactory;
    private MovieDataSource movieDataSource;

    public MoviePagedListRepository(APIService apiService) {
        this.apiService=apiService;
    }

    public LiveData<PagedList<Movie>> fetchLiveMoviePagedList(CompositeDisposable compositeDisposable) {
        moviesDataSourceFactory=new MovieDataSourceFactory(compositeDisposable, apiService);
        movieDataSource=new MovieDataSource(apiService, compositeDisposable);
        PagedList.Config config=new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(Constants.POST_PER_PAGE)
                .build();

        moviePagedList=new LivePagedListBuilder<>(moviesDataSourceFactory, config).build();
        return moviePagedList;
    }
}
