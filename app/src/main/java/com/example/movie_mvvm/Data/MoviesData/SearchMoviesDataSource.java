package com.example.movie_mvvm.Data.MoviesData;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.example.movie_mvvm.Entities.Movies.Movie;
import com.example.movie_mvvm.Entities.Movies.MovieResponse;
import com.example.movie_mvvm.NetworkServices.APIService;
import com.example.movie_mvvm.Utilities.Constants;

import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SearchMoviesDataSource extends PageKeyedDataSource<Integer, Movie> {

    private APIService apiService;
    private CompositeDisposable compositeDisposable;
    private int page = Constants.FIRST_PAGE;
    private MutableLiveData<Integer> total_results=new MutableLiveData<>();
    private String query;

    public SearchMoviesDataSource(APIService apiService, CompositeDisposable compositeDisposable, String query) {
        this.apiService=apiService;
        this.compositeDisposable=compositeDisposable;
        this.query=query;
    }

    public MutableLiveData<Integer> get_total_results() {return total_results;}

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Movie> callback) {
        compositeDisposable.add(
                apiService.get_searched_movies(query, page)
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableSingleObserver<MovieResponse>() {
                            @Override
                            public void onSuccess(MovieResponse movieResponse) {
                                callback.onResult(movieResponse.get_results(), null,
                                        page+1);
                                total_results.postValue(movieResponse.getTotalResults());
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("Connection", "Disconnected-load-initial");
                            }
                        })
        );
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) { }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
        compositeDisposable.add(
                apiService.get_searched_movies(query, params.key)
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableSingleObserver<MovieResponse>() {
                            @Override
                            public void onSuccess(MovieResponse movieResponse) {
                                if(movieResponse.get_total_pages() >= params.key) {
                                    callback.onResult(movieResponse.get_results(), params.key+1);
                                    Log.d("Connection", "Connected");
                                }
                                else {
                                    Log.d("Connection", "End of list");
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("Connection", "Disconnected-load-after");
                                Log.e("MovieDetailDataSource", Objects.requireNonNull(e.toString()));
                            }
                        })
        );
    }
}
