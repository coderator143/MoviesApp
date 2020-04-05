package com.example.movie_mvvm.Data.Repository.MovieRepositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movie_mvvm.Data.API.APIService;
import com.example.movie_mvvm.Data.VO.Movies.MovieCast;
import com.example.movie_mvvm.Data.VO.Movies.MovieCredits;
import com.example.movie_mvvm.Data.VO.Movies.MovieDetails;

import java.util.List;
import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailsNetworkDataSource {

    private APIService apiService;
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<MovieDetails> _downloadedMovieDetailsResponse = new MutableLiveData<MovieDetails>();
    private MutableLiveData<List<MovieCast>> _downloadedMovieCastResponse = new MutableLiveData<>();

    public MovieDetailsNetworkDataSource(APIService apiService, CompositeDisposable compositeDisposable) {
        this.apiService=apiService;
        this.compositeDisposable=compositeDisposable;
    }

    public LiveData<MovieDetails> get_DownloadedMovieDetailsResponse() { return _downloadedMovieDetailsResponse; }

    public LiveData<List<MovieCast>> get_DownloadedMovieCastResponse() { return _downloadedMovieCastResponse;}

    public void fetch_movie_details(int movieID) {
        try {
            compositeDisposable.add(
                    apiService.get_movie_details(movieID)
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableSingleObserver<MovieDetails>() {
                        @Override
                        public void onSuccess(MovieDetails o) {
                            _downloadedMovieDetailsResponse.postValue(o);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("MovieDetailDataSource", Objects.requireNonNull(e.toString()));
                        }
                    })
            );
        } catch (Exception e) {
            Log.e("MovieDetailDataSource",e.toString());
        }
    }

    public void fetch_movie_cast(int movieID) {
        try {
            compositeDisposable.add(
                    apiService.get_movie_credits(movieID)
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableSingleObserver<MovieCredits>() {
                        @Override
                        public void onSuccess(MovieCredits movieCredits) {
                            _downloadedMovieCastResponse.postValue(movieCredits.getMovieCast());
                            Log.d("cast1", "success");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("MovieDetailDataSource", Objects.requireNonNull(e.toString()));
                        }
                    })
            );
        } catch (Exception e) {
            Log.e("MovieDetailDataSource",e.toString());
        }
    }
}
