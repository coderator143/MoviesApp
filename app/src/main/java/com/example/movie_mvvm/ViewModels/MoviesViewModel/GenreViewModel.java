package com.example.movie_mvvm.ViewModels.MoviesViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.movie_mvvm.Entities.Movies.Genres;
import com.example.movie_mvvm.Repositories.DetailsRepository;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class GenreViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    public LiveData<List<Genres>> genreList;

    public GenreViewModel(DetailsRepository movieRepository, int movieID) {
        genreList=movieRepository.fetchingGenresListDetails(compositeDisposable, movieID);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
