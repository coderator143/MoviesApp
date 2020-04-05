package com.example.movie_mvvm.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.example.movie_mvvm.Entities.Movie;
import com.example.movie_mvvm.Repositories.MoviePagedListRepository;

import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;

public class MovieFragmentViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    private MoviePagedListRepository movieRepository;
    public LiveData<PagedList<Movie>> moviePagedList;

    public MovieFragmentViewModel(MoviePagedListRepository movieRepository) {
        this.movieRepository=movieRepository;
        moviePagedList=movieRepository.fetchLiveMoviePagedList(compositeDisposable);
    }

    public boolean isListEmpty() {return Objects.requireNonNull(moviePagedList.getValue()).isEmpty();}

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
