package com.example.movie_mvvm.Data.API;

import com.example.movie_mvvm.Data.VO.Movies.MovieDetails;
import com.example.movie_mvvm.Data.VO.Movies.MovieResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    @GET("movie/popular")
    Single<MovieResponse> get_popular_movie(@Query("page") int page);

    @GET("movie/{movie_id}")
    Single<MovieDetails> get_movie_details(@Path("movie_id") int id);
}
