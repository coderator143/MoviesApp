package com.example.movie_mvvm.Data.Repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movie_mvvm.Data.API.TheMovieDBInterface;
import com.example.movie_mvvm.Data.VO.MovieDetails;

import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailsNetworkDataSource {

    private TheMovieDBInterface apiService;
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<NetworkState> _networkState=new MutableLiveData<NetworkState>();
    private MutableLiveData<MovieDetails> _downloadedMovieDetailsResponse = new MutableLiveData<MovieDetails>();

    public MovieDetailsNetworkDataSource(TheMovieDBInterface apiService, CompositeDisposable compositeDisposable) {
        this.apiService=apiService;
        this.compositeDisposable=compositeDisposable;
    }

    public LiveData<NetworkState> get_NetworkState() { return _networkState; }

    public LiveData<MovieDetails> get_DownloadedMovieDetailsResponse() { return _downloadedMovieDetailsResponse; }

    public void fetch_movie_details(int movieID) {
        _networkState.postValue(NetworkState.Companion.LOADING);
        try {
            compositeDisposable.add(
                    apiService.get_movie_details(movieID)
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableSingleObserver<MovieDetails>() {
                        @Override
                        public void onSuccess(MovieDetails o) {
                            _downloadedMovieDetailsResponse.postValue(o);
                            _networkState.postValue(NetworkState.Companion.LOADED);
                        }

                        @Override
                        public void onError(Throwable e) {
                            _networkState.postValue(NetworkState.Companion.ERROR);
                            Log.e("MovieDetailDataSource", Objects.requireNonNull(e.toString()));
                        }
                    })
            );
        } catch (Exception e) {
            Log.e("MovieDetailDataSource",e.toString());
        }
    }
}
