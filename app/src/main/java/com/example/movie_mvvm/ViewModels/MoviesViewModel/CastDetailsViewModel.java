package com.example.movie_mvvm.ViewModels.MoviesViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.movie_mvvm.Entities.Movies.CastDetails;
import com.example.movie_mvvm.Repositories.DetailsRepository;
import io.reactivex.disposables.CompositeDisposable;

public class CastDetailsViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    public LiveData<CastDetails> castDetails;

    public CastDetailsViewModel(DetailsRepository movieRepository, int castId) {
        castDetails=movieRepository.fetchingSingleCastDetails(compositeDisposable, castId);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
