package com.example.movie_mvvm.Data.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.example.movie_mvvm.Data.API.TheMovieDBClient;
import com.example.movie_mvvm.Data.API.TheMovieDBInterface;
import com.example.movie_mvvm.Data.VO.Movie;
import com.example.movie_mvvm.Data.VO.MovieResponse;

import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MovieDataSource extends PageKeyedDataSource<Integer, Movie> {

    private TheMovieDBInterface apiService;
    private CompositeDisposable compositeDisposable;
    private int page= TheMovieDBClient.FIRST_PAGE;
    public MutableLiveData<NetworkState> networkState=new MutableLiveData<>();

    public MovieDataSource(TheMovieDBInterface apiService, CompositeDisposable compositeDisposable) {
        this.apiService=apiService;
        this.compositeDisposable=compositeDisposable;
    }

    public LiveData<NetworkState> get_NetworkState() { return networkState; }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Movie> callback) {
        networkState.postValue(NetworkState.Companion.LOADING);
        compositeDisposable.add(
                apiService.get_popular_movie(page)
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableSingleObserver<MovieResponse>() {
                            @Override
                            public void onSuccess(MovieResponse movieResponse) {
                                callback.onResult(movieResponse.get_results(), null, page+1);
                                networkState.postValue(NetworkState.Companion.LOADED);
                                Log.d("abcd", "Success");
                            }

                            @Override
                            public void onError(Throwable e) {
                                networkState.postValue(NetworkState.Companion.ERROR);
                                Log.e("MovieDetailDataSource", Objects.requireNonNull(e.toString()));
                            }
                        })
        );
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
        networkState.postValue(NetworkState.Companion.LOADING);
        compositeDisposable.add(
                apiService.get_popular_movie(params.key)
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableSingleObserver<MovieResponse>() {
                            @Override
                            public void onSuccess(MovieResponse movieResponse) {
                                if(movieResponse.get_total_pages() >= params.key) {
                                    callback.onResult(movieResponse.get_results(), params.key+1);
                                    networkState.postValue(NetworkState.Companion.LOADED);
                                }
                                else {
                                    networkState.postValue(NetworkState.Companion.ENDOFLIST);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                networkState.postValue(NetworkState.Companion.ERROR);
                                Log.e("MovieDetailDataSource", Objects.requireNonNull(e.toString()));
                            }
                        })
        );
    }
}
