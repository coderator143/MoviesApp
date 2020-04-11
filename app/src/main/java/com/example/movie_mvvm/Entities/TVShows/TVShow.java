package com.example.movie_mvvm.Entities.TVShows;

import com.google.gson.annotations.SerializedName;

public class TVShow {
    private int id;
    @SerializedName("poster_path") private String posterPath;
    @SerializedName("first_air_date") private String firstAirDate;
    private String name;

    public TVShow(int id, String posterPath, String firstAirDate, String name) {
        this.id=id;
        this.posterPath=posterPath;
        this.firstAirDate=firstAirDate;
        this.name=name;
    }

    //id
    public int get_tvId() {return id;}
    public void set_tvId(int id) {this.id=id;}

    //posterPath
    public String get_tvPosterPath() {return posterPath;}
    public void set_tvPosterPath(String posterPath) {this.posterPath=posterPath;}

    //releaseDate
    public String get_firstAirDate() {return firstAirDate;}
    public void set_firstAirDate(String firstAirDate) {this.firstAirDate=firstAirDate;}

    //title
    public String get_tvShowName() {return name;}
    public void set_tvShowName(String name) {this.name=name;}
}