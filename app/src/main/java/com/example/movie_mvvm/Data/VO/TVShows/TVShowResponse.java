package com.example.movie_mvvm.Data.VO.TVShows;

import com.example.movie_mvvm.Data.VO.TVShows.TVShow;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TVShowResponse {

    private int page;
    private int total_pages;
    private int total_results;
    @SerializedName("results") private List<TVShow> tvShowList;

    public TVShowResponse(int page, int total_pages, int total_results, List<TVShow> tvShowList) {
        this.page=page;
        this.total_pages=total_pages;
        this.total_results=total_results;
        this.tvShowList = tvShowList;
    }

    //page
    public int get_tvPage() { return page; }
    public void set_tvPage(int page) { this.page=page; }

    //total_pages
    public int get_tvTotalPages() { return total_pages; }
    public void set_tvTotalPages(int total_results) { this.total_results=total_results; }

    //total_results
    public int get_tvTotalResults() { return total_results; }
    public void set_tvTotalResults(int total_results) { this.total_results=total_results; }

    //tvShowsList
    public List<TVShow> get_tvShowsList() { return tvShowList; }
    public void set_tvShowsList(List<TVShow> tvShowList) { this.tvShowList = tvShowList; }
}
