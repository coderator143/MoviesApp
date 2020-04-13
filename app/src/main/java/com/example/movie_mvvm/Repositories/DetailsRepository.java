package com.example.movie_mvvm.Repositories;

import androidx.lifecycle.LiveData;

import com.example.movie_mvvm.Data.TvShowsData.TvShowsDetailsNetworkDataSource;
import com.example.movie_mvvm.Entities.Movies.CastDetails;
import com.example.movie_mvvm.Entities.Movies.Movie;
import com.example.movie_mvvm.Entities.TVShows.TVShow;
import com.example.movie_mvvm.NetworkServices.APIService;
import com.example.movie_mvvm.Data.MoviesData.MovieDetailsNetworkDataSource;
import com.example.movie_mvvm.Entities.Movies.MovieCast;
import com.example.movie_mvvm.Entities.Movies.MovieDetails;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class DetailsRepository {

    private APIService apiService;
    private MovieDetailsNetworkDataSource movieDetailsNetworkDataSource;
    private TvShowsDetailsNetworkDataSource tvShowsDetailsNetworkDataSource;

    public DetailsRepository(APIService apiService) {
        this.apiService=apiService;
    }

    public LiveData<MovieDetails> fetchingSingleMovieDetails(CompositeDisposable compositeDisposable, int movieId) {
        movieDetailsNetworkDataSource=new MovieDetailsNetworkDataSource(apiService, compositeDisposable);
        movieDetailsNetworkDataSource.fetch_movie_details(movieId);
        return movieDetailsNetworkDataSource.get_DownloadedMovieDetailsResponse();
    }

    public LiveData<List<MovieCast>> fetchingSingleMovieCast(CompositeDisposable compositeDisposable, int movieId) {
        movieDetailsNetworkDataSource=new MovieDetailsNetworkDataSource(apiService, compositeDisposable);
        movieDetailsNetworkDataSource.fetch_movie_cast(movieId);
        return movieDetailsNetworkDataSource.get_DownloadedMovieCastResponse();
    }

    public LiveData<List<Movie>> fetchingTwentyPopularMovies(CompositeDisposable compositeDisposable) {
        movieDetailsNetworkDataSource=new MovieDetailsNetworkDataSource(apiService, compositeDisposable);
        movieDetailsNetworkDataSource.fetch_homepage_movies();
        return movieDetailsNetworkDataSource.get_DownloadedHomePageMovieResponse();
    }

    public LiveData<CastDetails> fetchingSingleCastDetails(CompositeDisposable compositeDisposable, int castId) {
        movieDetailsNetworkDataSource=new MovieDetailsNetworkDataSource(apiService, compositeDisposable);
        movieDetailsNetworkDataSource.fetch_cast_details(castId);
        return movieDetailsNetworkDataSource.get_DownloadedCastDetailsResponse();
    }

    public LiveData<List<TVShow>> fetchingTwentyPopularTVShow(CompositeDisposable compositeDisposable) {
        tvShowsDetailsNetworkDataSource=new TvShowsDetailsNetworkDataSource(apiService, compositeDisposable);
        tvShowsDetailsNetworkDataSource.fetch_homepage_tvShows();
        return tvShowsDetailsNetworkDataSource.get_DownloadedHomePageTvShowResponse();
    }
}
