package com.example.movie_mvvm.UI.Single_Movie_Details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movie_mvvm.Data.VO.Movies.MovieCast;
import com.example.movie_mvvm.Data.VO.Movies.MovieDetails;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class SingleMovieViewModel extends ViewModel {

    private MovieDetailsRepository movieRepository;
    private int movieId;
    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    public LiveData<MovieDetails> moviedetails;
    public LiveData<List<MovieCast>> movieCast;

    public SingleMovieViewModel(MovieDetailsRepository movieRepository, int movieId) {
        this.movieRepository=movieRepository;
        this.movieId=movieId;
        moviedetails=movieRepository.fetchingSingleMovieDetails(compositeDisposable, movieId);
        movieCast=movieRepository.fetchingSingleMovieCast(compositeDisposable, movieId);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
