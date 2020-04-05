package com.example.movie_mvvm.Entities;

import com.google.gson.annotations.SerializedName;

public class Movie {

    private int id;
    @SerializedName("poster_path") private String posterPath;
    @SerializedName("release_date") private String releaseDate;
    private String title;

    public Movie(int id, String posterPath, String releaseDate, String title) {
        this.id=id;
        this.posterPath=posterPath;
        this.releaseDate=releaseDate;
        this.title=title;
    }

    //id
    public int get_id() {return id;}
    public void set_id(int id) {this.id=id;}

    //posterPath
    public String get_poster_path() {return posterPath;}
    public void set_poster_path(String posterPath) {this.posterPath=posterPath;}

    //releaseDate
    public String get_release_date() {return releaseDate;}
    public void set_release_date(String releaseDate) {this.releaseDate=releaseDate;}

    //title
    public String get_title() {return title;}
    public void set_title(String title) {this.title=title;}
}
