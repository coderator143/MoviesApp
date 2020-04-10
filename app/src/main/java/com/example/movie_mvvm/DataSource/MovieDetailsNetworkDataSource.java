package com.example.movie_mvvm.DataSource;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movie_mvvm.Entities.Movie;
import com.example.movie_mvvm.Entities.MovieResponse;
import com.example.movie_mvvm.NetworkServices.APIService;
import com.example.movie_mvvm.Entities.MovieCast;
import com.example.movie_mvvm.Entities.MovieCredits;
import com.example.movie_mvvm.Entities.MovieDetails;

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
    private MutableLiveData<List<Movie>> _downloadedHomePageMovieResponse = new MutableLiveData<>();

    public MovieDetailsNetworkDataSource(APIService apiService, CompositeDisposable compositeDisposable) {
        this.apiService=apiService;
        this.compositeDisposable=compositeDisposable;
    }

    public LiveData<MovieDetails> get_DownloadedMovieDetailsResponse() { return _downloadedMovieDetailsResponse; }

    public LiveData<List<MovieCast>> get_DownloadedMovieCastResponse() { return _downloadedMovieCastResponse;}

    public LiveData<List<Movie>> get_DownloadedHomePageMovieResponse() { return _downloadedHomePageMovieResponse; }

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

    public void fetch_homepage_movies() {
        try {
            compositeDisposable.add(
                    apiService.get_popular_movie(1)
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableSingleObserver<MovieResponse>() {
                        @Override
                        public void onSuccess(MovieResponse movieResponse) {
                            _downloadedHomePageMovieResponse.postValue(movieResponse.get_results());
                        }

                        @Override
                        public void onError(Throwable e) { }
                    })
            );
        }
        catch (Exception e) {
            Log.e("MovieDetailDataSource",e.toString());
        }
    }
}