package com.example.movie_mvvm.Data.Repository.MovieRepositories;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.example.movie_mvvm.Data.API.APIService;
import com.example.movie_mvvm.Data.VO.Movies.Movie;
import com.example.movie_mvvm.Data.VO.Movies.MovieResponse;
import com.example.movie_mvvm.UI.MainActivity;
import com.example.movie_mvvm.UI.PopularMovie.MovieFragment;
import com.example.movie_mvvm.Utilities.Constants;
import com.example.movie_mvvm.Utilities.MyApplication;

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
                        Log.d("Connection", "Connected");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Connection", "Disconnected-load-initial");
                    }
                })
        );
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {

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
