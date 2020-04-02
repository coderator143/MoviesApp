package com.example.movie_mvvm.Data.VO.Movies;

import com.example.movie_mvvm.Data.VO.Movies.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {

    private int page;
    @SerializedName("results") private List<Movie> movieList;
    @SerializedName("total_pages") private int totalPages;

    public MovieResponse(int page, List<Movie> movieList, int totalPages) {
        this.page=page;
        this.movieList=movieList;
        this.totalPages=totalPages;
    }

    //page
    public int get_page() {return page;}
    public void set_page(int page) {this.page=page;}

    //results
    public List<Movie> get_results() {return movieList;}
    public void set_results(List<Movie> movieList) {this.movieList=movieList;}

    //totalPages
    public int get_total_pages() {return totalPages;}
    public void set_total_pages(int totalPages) {this.totalPages=totalPages;}
}
