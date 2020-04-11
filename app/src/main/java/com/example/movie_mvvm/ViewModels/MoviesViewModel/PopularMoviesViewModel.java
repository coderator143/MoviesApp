package com.example.movie_mvvm.ViewModels.MoviesViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;
import com.example.movie_mvvm.Entities.Movies.Movie;
import com.example.movie_mvvm.Repositories.PagedListRepository;
import io.reactivex.disposables.CompositeDisposable;

public class PopularMoviesViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    public LiveData<PagedList<Movie>> moviePagedList;

    public PopularMoviesViewModel(PagedListRepository movieRepository) {
        moviePagedList=movieRepository.fetchLiveMoviePagedList(compositeDisposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
