package com.example.movie_mvvm.Data.TvShowsData;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import com.example.movie_mvvm.Entities.TVShows.TVShow;
import com.example.movie_mvvm.NetworkServices.APIService;

import io.reactivex.disposables.CompositeDisposable;

public class TvShowsDataSourceFactory extends DataSource.Factory<Integer, TVShow> {

    private CompositeDisposable compositeDisposable;
    private APIService apiService;
    private MutableLiveData<TvShowsDataSource> tvShowLiveDataSource=new MutableLiveData<>();

    public TvShowsDataSourceFactory(CompositeDisposable compositeDisposable, APIService apiService) {
        this.compositeDisposable=compositeDisposable;
        this.apiService=apiService;
    }

    @NonNull
    @Override
    public DataSource<Integer, TVShow> create() {
        TvShowsDataSource tvDataSource=new TvShowsDataSource(apiService, compositeDisposable);
        tvShowLiveDataSource.postValue(tvDataSource);
        return tvDataSource;
    }
}
