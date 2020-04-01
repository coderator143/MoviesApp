package com.example.movie_mvvm.UI.Single_Movie_Details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movie_mvvm.Data.Repository.NetworkState;
import com.example.movie_mvvm.Data.VO.Movies.MovieDetails;

import io.reactivex.disposables.CompositeDisposable;

public class SingleMovieViewModel extends ViewModel {

    private MovieDetailsRepository movieRepository;
    private int movieId;
    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    public LiveData<MovieDetails> moviedetails;
    public LiveData<NetworkState> networkState;

    public SingleMovieViewModel(MovieDetailsRepository movieRepository, int movieId) {
        this.movieRepository=movieRepository;
        this.movieId=movieId;
        moviedetails=movieRepository.fetchingSingleMovieDetails(compositeDisposable, movieId);
        networkState=movieRepository.getMovieDetailNetworkState();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
