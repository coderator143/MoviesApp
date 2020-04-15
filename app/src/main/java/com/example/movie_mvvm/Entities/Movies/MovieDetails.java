package com.example.movie_mvvm.Entities.Movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDetails {

    private String runtime;
    private String overview;
    @SerializedName("poster_path") private String posterPath;
    private String tagline;
    private String title;
    @SerializedName("vote_average") double rating;
    private List<Genres> genres;

    public MovieDetails(String runtime, String overview, String posterPath, String tagline, String title, double rating,
                        List<Genres> genres) {
        this.runtime=runtime;
        this.overview=overview;
        this.posterPath=posterPath;
        this.tagline=tagline;
        this.title=title;
        this.rating=rating;
        this.genres=genres;
    }

    //runtime
    public String get_movie_runtime() {return runtime;}
    public void set_movie_runtime(String runtime) {this.runtime=runtime;}

    //overview
    public String get_movie_overview() {return overview;}
    public void set_movie_overview(String overview) {this.overview=overview;}

    //posterPath
    public String get_movie_poster_path() {return posterPath;}
    public void set_movie_poster_path(String poster_path) {this.posterPath=poster_path;}

    //tagline
    public String get_movie_tagline() {return tagline;}
    public void set_movie_tagline(String tagline) {this.tagline=tagline;}

    //title
    public String get_movie_title() {return title;}
    public void set_movie_title(String title) {this.title=title;}

    //rating
    public double get_movie_rating() {return rating;}
    public void set_movie_rating(double rating) {this.rating=rating;}

    //genres
    public List<Genres> get_movie_genre_list() { return genres; }
    public void set_movie_genre_list(List<Genres> genres) { this.genres=genres; }
}
