package com.example.movie_mvvm.Data.TvShowsData;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import com.example.movie_mvvm.Entities.TVShows.TVShow;
import com.example.movie_mvvm.Entities.TVShows.TVShowResponse;
import com.example.movie_mvvm.NetworkServices.APIService;
import com.example.movie_mvvm.Utilities.Constants;

import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class TvShowsDataSource extends PageKeyedDataSource<Integer, TVShow> {

    private APIService apiService;
    private CompositeDisposable compositeDisposable;
    private int page = Constants.FIRST_PAGE;

    public TvShowsDataSource(APIService apiService, CompositeDisposable compositeDisposable) {
        this.apiService=apiService;
        this.compositeDisposable=compositeDisposable;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, TVShow> callback) {
        compositeDisposable.add(
                apiService.get_popular_tv_shows(page)
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableSingleObserver<TVShowResponse>() {
                            @Override
                            public void onSuccess(TVShowResponse tvShowResponse) {
                                callback.onResult(tvShowResponse.get_tvShowsList(), null, page+1);
                                Log.d("Status", "Connected");
                            }

                            @Override
                            public void onError(Throwable e) { }
                        })
        );
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, TVShow> callback) {
        Log.d("Status", "Loading");
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, TVShow> callback) {
        compositeDisposable.add(
                apiService.get_popular_tv_shows(params.key)
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableSingleObserver<TVShowResponse>() {
                            @Override
                            public void onSuccess(TVShowResponse tvShowResponse) {
                                if(tvShowResponse.get_tvTotalPages() >= params.key) {
                                    callback.onResult(tvShowResponse.get_tvShowsList(), params.key+1);
                                    Log.d("Status", "Next page connected");
                                }
                                else Log.d("Status", "End of list");
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

