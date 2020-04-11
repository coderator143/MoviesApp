package com.example.movie_mvvm.Data.MoviesData;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import com.example.movie_mvvm.NetworkServices.APIService;
import com.example.movie_mvvm.Entities.Movies.Movie;
import com.example.movie_mvvm.Entities.Movies.MovieResponse;
import com.example.movie_mvvm.Utilities.Constants;
import java.util.Objects;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MovieDataSource extends PageKeyedDataSource<Integer, Movie> {

    private APIService apiService;
    private CompositeDisposable compositeDisposable;
    private int page= Constants.FIRST_PAGE;

    public MovieDataSource(APIService apiService, CompositeDisposable compositeDisposable) {
        this.apiService=apiService;
        this.compositeDisposable=compositeDisposable;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Movie> callback) {
        compositeDisposable.add(
            apiService.get_popular_movie(page)
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<MovieResponse>() {
                    @Override
                    public void onSuccess(MovieResponse movieResponse) {
                        callback.onResult(movieResponse.get_results(), null, page+1);
                        Log.d("Status", "Connected");
                    }

                    @Override
                    public void onError(Throwable e) { }
                })
        );
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
        Log.d("Status", "Loading");
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
        compositeDisposable.add(
                apiService.get_popular_movie(params.key)
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableSingleObserver<MovieResponse>() {
                            @Override
                            public void onSuccess(MovieResponse movieResponse) {
                                if(movieResponse.get_total_pages() >= params.key) {
                                    callback.onResult(movieResponse.get_results(), params.key+1);
                                    Log.d("Status", "Next page connected");
                                }
                                else {
                                    Log.d("Status", "End of list");
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
