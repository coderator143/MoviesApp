package com.example.movie_mvvm.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movie_mvvm.Entities.Movie;
import com.example.movie_mvvm.Entities.MovieCast;
import com.example.movie_mvvm.Entities.MovieDetails;
import com.example.movie_mvvm.Repositories.MovieDetailsRepository;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class MovieHomepageViewModel extends ViewModel {

    private MovieDetailsRepository movieRepository;
    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    public LiveData<List<Movie>> popularMovies;

    public MovieHomepageViewModel(MovieDetailsRepository movieRepository) {
        this.movieRepository=movieRepository;
        popularMovies=movieRepository.fetchingTwentyPopularMovies(compositeDisposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
