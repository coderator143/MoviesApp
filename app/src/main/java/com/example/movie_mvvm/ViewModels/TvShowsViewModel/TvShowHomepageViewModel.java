package com.example.movie_mvvm.ViewModels.TvShowsViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.movie_mvvm.Entities.TVShows.TVShow;
import com.example.movie_mvvm.Repositories.DetailsRepository;
import java.util.List;
import io.reactivex.disposables.CompositeDisposable;

public class TvShowHomepageViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    public LiveData<List<TVShow>> popularTvShows;

    public TvShowHomepageViewModel(DetailsRepository detailsRepository) {
        popularTvShows=detailsRepository.fetchingTwentyPopularTVShow(compositeDisposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
