package com.example.movie_mvvm.Data.MoviesData;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.movie_mvvm.NetworkServices.APIService;
import com.example.movie_mvvm.Entities.Movies.Movie;

import io.reactivex.disposables.CompositeDisposable;

public class MovieDataSourceFactory extends DataSource.Factory<Integer, Movie> {

    private CompositeDisposable compositeDisposable;
    private APIService apiService;
    private MutableLiveData<MovieDataSource> moviesLiveDataSource=new MutableLiveData<>();

    public MovieDataSourceFactory(CompositeDisposable compositeDisposable, APIService apiService) {
        this.compositeDisposable=compositeDisposable;
        this.apiService=apiService;
    }

    @NonNull
    @Override
    public DataSource<Integer, Movie> create() {
        MovieDataSource movieDataSource=new MovieDataSource(apiService, compositeDisposable);
        moviesLiveDataSource.postValue(movieDataSource);
        return movieDataSource;
    }
}
