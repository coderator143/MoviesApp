package com.example.movie_mvvm.Data.TvShowsData;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.movie_mvvm.Entities.TVShows.TVShow;
import com.example.movie_mvvm.Entities.TVShows.TVShowResponse;
import com.example.movie_mvvm.NetworkServices.APIService;
import java.util.List;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class TvShowsDetailsNetworkDataSource {

    private APIService apiService;
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<List<TVShow>> _downloadedHomePageTvShowResponse = new MutableLiveData<>();

    public TvShowsDetailsNetworkDataSource(APIService apiService, CompositeDisposable compositeDisposable) {
        this.apiService=apiService;
        this.compositeDisposable=compositeDisposable;
    }

    public LiveData<List<TVShow>> get_DownloadedHomePageTvShowResponse() { return _downloadedHomePageTvShowResponse; }

    public void fetch_homepage_tvShows() {
        try {
            compositeDisposable.add(
                    apiService.get_popular_tv_shows(1)
                            .subscribeOn(Schedulers.io())
                            .subscribeWith(new DisposableSingleObserver<TVShowResponse>() {
                                @Override
                                public void onSuccess(TVShowResponse tvShowResponse) {
                                    _downloadedHomePageTvShowResponse.postValue(tvShowResponse.get_tvShowsList());
                                }

                                @Override
                                public void onError(Throwable e) { }
                            })
            );
        } catch (Exception e) { Log.e("MovieDetailDataSource",e.toString()); }
    }

}
