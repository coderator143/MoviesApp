package com.example.movie_mvvm.Data.VO;

import androidx.lifecycle.LiveData;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.xml.transform.Result;

public class MovieResponse {

    private int page;
    @SerializedName("results") private List<Movie> movieList;
    @SerializedName("total_pages") private int totalPages;
    @SerializedName("total_results") private int totalResults;

    public MovieResponse(int page, List<Movie> movieList, int totalPages, int totalResults) {
        this.page=page;
        this.movieList=movieList;
        this.totalPages=totalPages;
        this.totalResults=totalResults;
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

    //totalResults
    public int get_total_results() {return totalResults;}
    public void set_total_results(int totalResults) {this.totalResults=totalResults;}
}
