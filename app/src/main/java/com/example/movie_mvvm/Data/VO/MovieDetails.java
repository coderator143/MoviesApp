package com.example.movie_mvvm.Data.VO;

import com.google.gson.annotations.SerializedName;

public class MovieDetails {

    private int budget;
    private int id;
    private String overview;
    private double popularity;
    @SerializedName("poster_path") private String posterPath;
    @SerializedName("release_date") private String releaseDate;
    private long revenue;
    private int runtime;
    private String status;
    private String tagline;
    private String title;
    private boolean video;
    @SerializedName("vote_average") double rating;

    public MovieDetails(int budget, int id, String overview, double popularity, String posterPath, String releaseDate,
                        long revenue, int runtime, String status, String tagline, String title, boolean video) {
        this.budget=budget;
        this.id=id;
        this.overview=overview;
        this.popularity=popularity;
        this.posterPath=posterPath;
        this.releaseDate=releaseDate;
        this.revenue=revenue;
        this.runtime=runtime;
        this.status=status;
        this.tagline=tagline;
        this.title=title;
        this.video=video;
    }

    //budget
    public int get_movie_budget() {return budget;}
    public void set_movie_budget(int budget) {this.budget=budget;}

    //id
    public int set_movie_id() {return id;}
    public void set_movie_id(int id) {this.id=id;}

    //overview
    public String get_movie_overview() {return overview;}
    public void set_movie_overview(String overview) {this.overview=overview;}

    //popularity
    public double get_movie_popularity() {return popularity;}
    public void set_movie_popularity(double popularity) {this.popularity=popularity;}

    //posterPath
    public String get_movie_poster_path() {return posterPath;}
    public void set_movie_poster_path(String poster_path) {this.posterPath=poster_path;}

    //releaseDate
    public String get_movie_release_date() {return releaseDate;}
    public void set_movie_release_date(String release_date) {this.releaseDate=release_date;}

    //revenue
    public long get_movie_revenue() {return revenue;}
    public void set_movie_revenue(long revenue) {this.revenue=revenue;}

    //runtime
    public int get_movie_runtime() {return runtime;}
    public void set_movie_runtime(int runtime) {this.runtime=runtime;}

    //status
    public String get_movie_status() {return status;}
    public void set_movie_status(String status) {this.status=status;}

    //tagline
    public String get_movie_tagline() {return tagline;}
    public void set_movie_tagline(String tagline) {this.tagline=tagline;}

    //title
    public String get_movie_title() {return title;}
    public void set_movie_title(String title) {this.title=title;}

    //video
    public boolean get_movie_video() {return video;}
    public void set_movie_video(boolean video) {this.video=video;}

    //rating
    public double get_movie_rating() {return rating;}
    public void set_movie_rating(double rating) {this.rating=rating;}
}
