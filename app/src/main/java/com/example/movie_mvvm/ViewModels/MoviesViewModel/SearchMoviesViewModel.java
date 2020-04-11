package com.example.movie_mvvm.ViewModels.MoviesViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;
import com.example.movie_mvvm.Entities.Movies.Movie;
import com.example.movie_mvvm.Repositories.PagedListRepository;
import io.reactivex.disposables.CompositeDisposable;

public class SearchMoviesViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public LiveData<PagedList<Movie>> searchMoviePagedList;
    //public LiveData<Integer> totalResults;

    public SearchMoviesViewModel(PagedListRepository movieRepository, String query) {
        //totalResults=movieRepository.get_total_search_results(compositeDisposable, query);
        searchMoviePagedList = movieRepository.fetchLiveSearchMoviePagedList(compositeDisposable, query);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
