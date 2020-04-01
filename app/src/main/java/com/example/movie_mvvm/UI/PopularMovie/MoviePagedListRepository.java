package com.example.movie_mvvm.UI.PopularMovie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.movie_mvvm.Data.API.TheMovieDBClient;
import com.example.movie_mvvm.Data.API.TheMovieDBInterface;
import com.example.movie_mvvm.Data.Repository.MovieDataSource;
import com.example.movie_mvvm.Data.Repository.MovieDataSourceFactory;
import com.example.movie_mvvm.Data.Repository.MovieDetailsNetworkDataSource;
import com.example.movie_mvvm.Data.Repository.NetworkState;
import com.example.movie_mvvm.Data.VO.Movie;

import io.reactivex.disposables.CompositeDisposable;

public class MoviePagedListRepository {

    private TheMovieDBInterface apiService;
    private LiveData<PagedList<Movie>> moviePagedList;
    private MovieDataSourceFactory moviesDataSourceFactory;
    private MovieDataSource movieDataSource;

    public MoviePagedListRepository(TheMovieDBInterface apiService) {
        this.apiService=apiService;
    }

    LiveData<PagedList<Movie>> fetchLiveMoviePagedList(CompositeDisposable compositeDisposable) {
        moviesDataSourceFactory=new MovieDataSourceFactory(compositeDisposable, apiService);
        movieDataSource=new MovieDataSource(apiService, compositeDisposable);
        PagedList.Config config=new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(TheMovieDBClient.POST_PER_PAGE)
                .build();

        moviePagedList=new LivePagedListBuilder<>(moviesDataSourceFactory, config).build();
        return moviePagedList;
    }

    LiveData<NetworkState> getNetworkState() {
        return movieDataSource.get_NetworkState();
    }
}
