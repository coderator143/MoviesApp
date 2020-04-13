package com.example.movie_mvvm.ViewModels.MoviesViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.movie_mvvm.Entities.Movies.MovieCast;
import com.example.movie_mvvm.Entities.Movies.MovieDetails;
import com.example.movie_mvvm.Repositories.DetailsRepository;
import java.util.List;
import io.reactivex.disposables.CompositeDisposable;

public class SingleMovieViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    public LiveData<MovieDetails> moviedetails;
    public LiveData<List<MovieCast>> movieCast;

    public SingleMovieViewModel(DetailsRepository movieRepository, int movieId) {
        moviedetails=movieRepository.fetchingSingleMovieDetails(compositeDisposable, movieId);
        movieCast=movieRepository.fetchingSingleMovieCast(compositeDisposable, movieId);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
