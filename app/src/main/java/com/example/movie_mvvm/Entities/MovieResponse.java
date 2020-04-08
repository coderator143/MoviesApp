package com.example.movie_mvvm.Entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {

    @SerializedName("total_results") private int totalResults;
    @SerializedName("results") private List<Movie> movieList;
    @SerializedName("total_pages") private int totalPages;

    public MovieResponse(List<Movie> movieList, int totalPages, int totalResults) {
        this.movieList=movieList;
        this.totalPages=totalPages;
        this.totalResults=totalResults;
    }

    //totalResults
    public int getTotalResults() {return totalResults;}
    public void setTotalResults(int totalResults) {this.totalResults=totalResults;}

    //results
    public List<Movie> get_results() {return movieList;}
    public void set_results(List<Movie> movieList) {this.movieList=movieList;}

    //totalPages
    public int get_total_pages() {return totalPages;}
    public void set_total_pages(int totalPages) {this.totalPages=totalPages;}
}
