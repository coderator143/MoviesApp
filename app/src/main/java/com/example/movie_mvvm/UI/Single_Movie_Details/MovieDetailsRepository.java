package com.example.movie_mvvm.UI.Single_Movie_Details;

import androidx.lifecycle.LiveData;

import com.example.movie_mvvm.Data.API.APIService;
import com.example.movie_mvvm.Data.Repository.MovieRepositories.MovieDetailsNetworkDataSource;
import com.example.movie_mvvm.Data.VO.Movies.MovieCast;
import com.example.movie_mvvm.Data.VO.Movies.MovieDetails;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class MovieDetailsRepository {

    private APIService apiService;
    private MovieDetailsNetworkDataSource movieDetailsNetworkDataSource;

    public MovieDetailsRepository(APIService apiService) {
        this.apiService=apiService;
    }

    LiveData<MovieDetails> fetchingSingleMovieDetails(CompositeDisposable compositeDisposable, int movieId) {
        movieDetailsNetworkDataSource=new MovieDetailsNetworkDataSource(apiService, compositeDisposable);
        movieDetailsNetworkDataSource.fetch_movie_details(movieId);
        return movieDetailsNetworkDataSource.get_DownloadedMovieDetailsResponse();
    }

    LiveData<List<MovieCast>> fetchingSingleMovieCast(CompositeDisposable compositeDisposable, int movieId) {
        movieDetailsNetworkDataSource=new MovieDetailsNetworkDataSource(apiService, compositeDisposable);
        movieDetailsNetworkDataSource.fetch_movie_cast(movieId);
        return movieDetailsNetworkDataSource.get_DownloadedMovieCastResponse();
    }
}
