package com.example.movie_mvvm.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.example.movie_mvvm.Entities.Movie;
import com.example.movie_mvvm.Repositories.MoviePagedListRepository;

import io.reactivex.disposables.CompositeDisposable;

public class SearchMoviesViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MoviePagedListRepository movieRepository;
    public LiveData<PagedList<Movie>> searchMoviePagedList;
    public LiveData<Integer> totalResults;
    private String query;

    public SearchMoviesViewModel(MoviePagedListRepository movieRepository, String query) {
        this.movieRepository = movieRepository;
        this.query=query;
        totalResults=movieRepository.get_total_search_results(compositeDisposable, query);
        searchMoviePagedList = movieRepository.fetchLiveSearchMoviePagedList(compositeDisposable, query);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
