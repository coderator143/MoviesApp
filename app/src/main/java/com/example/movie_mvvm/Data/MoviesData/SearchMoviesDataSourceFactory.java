package com.example.movie_mvvm.Data.MoviesData;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.movie_mvvm.NetworkServices.APIService;
import com.example.movie_mvvm.Entities.Movies.Movie;

import io.reactivex.disposables.CompositeDisposable;

public class SearchMoviesDataSourceFactory extends DataSource.Factory<Integer, Movie> {

    private CompositeDisposable compositeDisposable;
    private APIService apiService;
    private MutableLiveData<SearchMoviesDataSource> searchMoviesDataSourceMutableLiveData=new MutableLiveData<>();
    private String query;

    public SearchMoviesDataSourceFactory(CompositeDisposable compositeDisposable, APIService apiService, String query) {
        this.query=query;
        this.compositeDisposable=compositeDisposable;
        this.apiService=apiService;
    }

    @NonNull
    @Override
    public DataSource<Integer, Movie> create() {
        SearchMoviesDataSource searchMoviesDataSource=new SearchMoviesDataSource(apiService, compositeDisposable, query);
        searchMoviesDataSourceMutableLiveData.postValue(searchMoviesDataSource);
        return searchMoviesDataSource;
    }
}