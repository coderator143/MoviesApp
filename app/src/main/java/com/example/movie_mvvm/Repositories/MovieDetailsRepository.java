package com.example.movie_mvvm.Repositories;

import androidx.lifecycle.LiveData;

import com.example.movie_mvvm.Entities.Movies.Movie;
import com.example.movie_mvvm.NetworkServices.APIService;
import com.example.movie_mvvm.Data.MoviesData.MovieDetailsNetworkDataSource;
import com.example.movie_mvvm.Entities.Movies.MovieCast;
import com.example.movie_mvvm.Entities.Movies.MovieDetails;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class MovieDetailsRepository {

    private APIService apiService;
    private MovieDetailsNetworkDataSource movieDetailsNetworkDataSource;

    public MovieDetailsRepository(APIService apiService) {
        this.apiService=apiService;
    }

    public LiveData<MovieDetails> fetchingSingleMovieDetails(CompositeDisposable compositeDisposable, int movieId) {
        movieDetailsNetworkDataSource=new MovieDetailsNetworkDataSource(apiService, compositeDisposable);
        movieDetailsNetworkDataSource.fetch_movie_details(movieId);
        return movieDetailsNetworkDataSource.get_DownloadedMovieDetailsResponse();
    }

    public LiveData<List<MovieCast>> fetchingSingleMovieCast(CompositeDisposable compositeDisposable, int movieId) {
        movieDetailsNetworkDataSource=new MovieDetailsNetworkDataSource(apiService, compositeDisposable);
        movieDetailsNetworkDataSource.fetch_movie_cast(movieId);
        return movieDetailsNetworkDataSource.get_DownloadedMovieCastResponse();
    }

    public LiveData<List<Movie>> fetchingTwentyPopularMovies(CompositeDisposable compositeDisposable) {
        movieDetailsNetworkDataSource=new MovieDetailsNetworkDataSource(apiService, compositeDisposable);
        movieDetailsNetworkDataSource.fetch_homepage_movies();
        return movieDetailsNetworkDataSource.get_DownloadedHomePageMovieResponse();
    }
}
