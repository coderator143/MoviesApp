package com.example.movie_mvvm.Entities.Movies;

import com.google.gson.annotations.SerializedName;

public class MovieDetails {

    private int budget;
    private String overview;
    @SerializedName("poster_path") private String posterPath;
    @SerializedName("release_date") private String releaseDate;
    private long revenue;
    private String tagline;
    private String title;
    @SerializedName("vote_average") double rating;

    public MovieDetails(int budget, String overview, String posterPath, String releaseDate,
                        long revenue, String tagline, String title) {
        this.budget=budget;
        this.overview=overview;
        this.posterPath=posterPath;
        this.releaseDate=releaseDate;
        this.revenue=revenue;
        this.tagline=tagline;
        this.title=title;
    }

    //budget
    public int get_movie_budget() {return budget;}
    public void set_movie_budget(int budget) {this.budget=budget;}

    //overview
    public String get_movie_overview() {return overview;}
    public void set_movie_overview(String overview) {this.overview=overview;}

    //posterPath
    public String get_movie_poster_path() {return posterPath;}
    public void set_movie_poster_path(String poster_path) {this.posterPath=poster_path;}

    //releaseDate
    public String get_movie_release_date() {return releaseDate;}
    public void set_movie_release_date(String release_date) {this.releaseDate=release_date;}

    //revenue
    public long get_movie_revenue() {return revenue;}
    public void set_movie_revenue(long revenue) {this.revenue=revenue;}

    //tagline
    public String get_movie_tagline() {return tagline;}
    public void set_movie_tagline(String tagline) {this.tagline=tagline;}

    //title
    public String get_movie_title() {return title;}
    public void set_movie_title(String title) {this.title=title;}

    //rating
    public double get_movie_rating() {return rating;}
    public void set_movie_rating(double rating) {this.rating=rating;}
}
