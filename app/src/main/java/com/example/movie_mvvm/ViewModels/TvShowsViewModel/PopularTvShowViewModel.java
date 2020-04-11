package com.example.movie_mvvm.ViewModels.TvShowsViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;
import com.example.movie_mvvm.Entities.TVShows.TVShow;
import com.example.movie_mvvm.Repositories.PagedListRepository;

import io.reactivex.disposables.CompositeDisposable;

public class PopularTvShowViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    public LiveData<PagedList<TVShow>> tvshowPagedList;

    public PopularTvShowViewModel(PagedListRepository movieRepository) {
        tvshowPagedList=movieRepository.fetchLiveSearchTvShowPagedList(compositeDisposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
