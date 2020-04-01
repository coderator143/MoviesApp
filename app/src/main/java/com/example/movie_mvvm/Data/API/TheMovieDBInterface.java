package com.example.movie_mvvm.Data.API;

import com.example.movie_mvvm.Data.VO.MovieDetails;
import com.example.movie_mvvm.Data.VO.MovieResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDBInterface {

    @GET("movie/popular")
    Single<MovieResponse> get_popular_movie(@Query("page") int page);

    @GET("movie/{movie_id}")
    Single<MovieDetails> get_movie_details(@Path("movie_id") int id);
}
