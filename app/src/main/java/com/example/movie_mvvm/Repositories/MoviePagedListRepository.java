package com.example.movie_mvvm.Repositories;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.movie_mvvm.DataSource.SearchMoviesDataSource;
import com.example.movie_mvvm.DataSource.SearchMoviesDataSourceFactory;
import com.example.movie_mvvm.NetworkServices.APIService;
import com.example.movie_mvvm.DataSource.MovieDataSource;
import com.example.movie_mvvm.DataSource.MovieDataSourceFactory;
import com.example.movie_mvvm.Entities.Movie;
import com.example.movie_mvvm.Utilities.Constants;

import io.reactivex.disposables.CompositeDisposable;

public class MoviePagedListRepository {

    private APIService apiService;
    private LiveData<PagedList<Movie>> moviePagedList, searchMoviePagedList;
    private MovieDataSourceFactory moviesDataSourceFactory;
    private SearchMoviesDataSourceFactory searchMoviesDataSourceFactory;
    private MovieDataSource movieDataSource;
    private SearchMoviesDataSource searchMoviesDataSource;

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

    public LiveData<PagedList<Movie>> fetchLiveSearchMoviePagedList(CompositeDisposable compositeDisposable, String query) {
        searchMoviesDataSourceFactory=new SearchMoviesDataSourceFactory(compositeDisposable, apiService, query);
        searchMoviesDataSource=new SearchMoviesDataSource(apiService, compositeDisposable, query);
        PagedList.Config config=new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(Constants.POST_PER_PAGE)
                .build();

        searchMoviePagedList=new LivePagedListBuilder<>(searchMoviesDataSourceFactory, config).build();
        return searchMoviePagedList;
    }

    public LiveData<Integer> get_total_search_results(CompositeDisposable compositeDisposable, String query) {
        searchMoviesDataSource=new SearchMoviesDataSource(apiService, compositeDisposable, query);
        return searchMoviesDataSource.get_total_results();
    }
}
