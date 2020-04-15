package com.example.movie_mvvm.Data.MoviesData;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movie_mvvm.Entities.Movies.CastDetails;
import com.example.movie_mvvm.Entities.Movies.Genres;
import com.example.movie_mvvm.Entities.Movies.Movie;
import com.example.movie_mvvm.Entities.Movies.MovieResponse;
import com.example.movie_mvvm.NetworkServices.APIService;
import com.example.movie_mvvm.Entities.Movies.MovieCast;
import com.example.movie_mvvm.Entities.Movies.MovieCredits;
import com.example.movie_mvvm.Entities.Movies.MovieDetails;

import java.util.List;
import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailsNetworkDataSource {

    private APIService apiService;
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<MovieDetails> _downloadedMovieDetailsResponse = new MutableLiveData<>();
    private MutableLiveData<List<MovieCast>> _downloadedMovieCastResponse = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> _downloadedHomePageMovieResponse = new MutableLiveData<>();
    private MutableLiveData<CastDetails> _downloadedCastDetailsResponse = new MutableLiveData<>();
    private MutableLiveData<List<Genres>> _downloadedGenreListResponse = new MutableLiveData<>();

    public MovieDetailsNetworkDataSource(APIService apiService, CompositeDisposable compositeDisposable) {
        this.apiService=apiService;
        this.compositeDisposable=compositeDisposable;
    }

    public LiveData<MovieDetails> get_DownloadedMovieDetailsResponse() { return _downloadedMovieDetailsResponse; }

    public LiveData<List<MovieCast>> get_DownloadedMovieCastResponse() { return _downloadedMovieCastResponse;}

    public LiveData<List<Movie>> get_DownloadedHomePageMovieResponse() { return _downloadedHomePageMovieResponse; }

    public LiveData<CastDetails> get_DownloadedCastDetailsResponse() { return _downloadedCastDetailsResponse; }

    public LiveData<List<Genres>> get_DownloadedGenreListResponse() { return _downloadedGenreListResponse; }

    public void fetch_movie_details(int movieID) {
        try {
            compositeDisposable.add(
                    apiService.get_movie_details(movieID)
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableSingleObserver<MovieDetails>() {
                        @Override
                        public void onSuccess(MovieDetails o) {
                            _downloadedMovieDetailsResponse.postValue(o);
                            _downloadedGenreListResponse.postValue(o.get_movie_genre_list());
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("MovieDetailDataSource", Objects.requireNonNull(e.toString()));
                        }
                    })
            );
        } catch (Exception e) { Log.e("MovieDetailDataSource",e.toString()); }
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
        } catch (Exception e) { Log.e("MovieDetailDataSource",e.toString()); }
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
        } catch (Exception e) { Log.e("MovieDetailDataSource",e.toString()); }
    }

    public void fetch_cast_details(int castId) {
        try {
            compositeDisposable.add(
                    apiService.get_cast_details(castId)
                            .subscribeOn(Schedulers.io())
                            .subscribeWith(new DisposableSingleObserver<CastDetails>() {
                                @Override
                                public void onSuccess(CastDetails castDetails) {
                                    _downloadedCastDetailsResponse.postValue(castDetails);
                                }

                                @Override
                                public void onError(Throwable e) { }
                            })
            );
        } catch (Exception e) { Log.e("MovieDetailDataSource",e.toString()); }
    }
}
