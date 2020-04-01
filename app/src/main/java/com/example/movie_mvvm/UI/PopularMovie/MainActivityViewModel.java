package com.example.movie_mvvm.UI.PopularMovie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.example.movie_mvvm.Data.Repository.NetworkState;
import com.example.movie_mvvm.Data.VO.Movie;

import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivityViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    private MoviePagedListRepository movieRepository;
    public LiveData<PagedList<Movie>> moviePagedList;
    public LiveData<NetworkState> networkState;

    public MainActivityViewModel(MoviePagedListRepository movieRepository) {
        this.movieRepository=movieRepository;
        moviePagedList=movieRepository.fetchLiveMoviePagedList(compositeDisposable);
        networkState=movieRepository.getNetworkState();
    }

    public boolean isListEmpty() {return Objects.requireNonNull(moviePagedList.getValue()).isEmpty();}

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
