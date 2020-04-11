package com.example.movie_mvvm.Repositories;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.example.movie_mvvm.Data.MoviesData.SearchMoviesDataSourceFactory;
import com.example.movie_mvvm.Data.TvShowsData.TvShowsDataSourceFactory;
import com.example.movie_mvvm.Entities.TVShows.TVShow;
import com.example.movie_mvvm.NetworkServices.APIService;
import com.example.movie_mvvm.Data.MoviesData.MovieDataSourceFactory;
import com.example.movie_mvvm.Entities.Movies.Movie;
import com.example.movie_mvvm.Utilities.Constants;

import io.reactivex.disposables.CompositeDisposable;

public class PagedListRepository {

    private APIService apiService;
    //private SearchMoviesDataSource searchMoviesDataSource;

    public PagedListRepository(APIService apiService) {
        this.apiService=apiService;
    }

    public LiveData<PagedList<Movie>> fetchLiveMoviePagedList(CompositeDisposable compositeDisposable) {
        MovieDataSourceFactory moviesDataSourceFactory = new MovieDataSourceFactory(compositeDisposable, apiService);
        //MovieDataSource movieDataSource = new MovieDataSource(apiService, compositeDisposable);
        PagedList.Config config=new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(Constants.POST_PER_PAGE)
                .build();

        return new LivePagedListBuilder<>(moviesDataSourceFactory, config).build();
    }

    public LiveData<PagedList<Movie>> fetchLiveSearchMoviePagedList(CompositeDisposable compositeDisposable, String query) {
        SearchMoviesDataSourceFactory searchMoviesDataSourceFactory = new SearchMoviesDataSourceFactory(compositeDisposable,
                apiService, query);
        //searchMoviesDataSource=new SearchMoviesDataSource(apiService, compositeDisposable, query);
        PagedList.Config config=new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(Constants.POST_PER_PAGE)
                .build();

        return new LivePagedListBuilder<>(searchMoviesDataSourceFactory, config).build();
    }

    public LiveData<PagedList<TVShow>> fetchLiveSearchTvShowPagedList(CompositeDisposable compositeDisposable) {
        TvShowsDataSourceFactory tvShowsDataSourceFactory=new TvShowsDataSourceFactory(compositeDisposable, apiService);
        PagedList.Config config=new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(Constants.POST_PER_PAGE)
                .build();

        return new LivePagedListBuilder<>(tvShowsDataSourceFactory, config).build();
    }

//    public LiveData<Integer> get_total_search_results(CompositeDisposable compositeDisposable, String query) {
//        searchMoviesDataSource=new SearchMoviesDataSource(apiService, compositeDisposable, query);
//        return searchMoviesDataSource.get_total_results();
//    }
}
