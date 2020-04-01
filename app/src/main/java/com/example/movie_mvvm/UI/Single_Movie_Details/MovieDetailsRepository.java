package com.example.movie_mvvm.UI.Single_Movie_Details;

import androidx.lifecycle.LiveData;

import com.example.movie_mvvm.Data.API.APIService;
import com.example.movie_mvvm.Data.Repository.MovieRepositories.MovieDetailsNetworkDataSource;
import com.example.movie_mvvm.Data.Repository.NetworkState;
import com.example.movie_mvvm.Data.VO.Movies.MovieDetails;

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

    LiveData<NetworkState> getMovieDetailNetworkState() {
        return movieDetailsNetworkDataSource.get_NetworkState();
    }
}
