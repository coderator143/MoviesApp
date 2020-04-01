package com.example.movie_mvvm.Data.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.movie_mvvm.Data.API.TheMovieDBInterface;
import com.example.movie_mvvm.Data.VO.Movie;

import io.reactivex.disposables.CompositeDisposable;

public class MovieDataSourceFactory extends DataSource.Factory<Integer, Movie> {

    private CompositeDisposable compositeDisposable;
    private TheMovieDBInterface apiService;
    public MutableLiveData<MovieDataSource> moviesLiveDataSource=new MutableLiveData<>();

    public MovieDataSourceFactory(CompositeDisposable compositeDisposable, TheMovieDBInterface apiService) {
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
