package com.example.movie_mvvm.Entities.Movies;

public class Genres {

    private int id;
    private String name;

    public Genres(int id, String name) {
        this.id=id;
        this.name=name;
    }

    //id
    public int getGenreID() {return id;}
    public void setGenreID(int id) {this.id=id;}

    //name
    public String getGenreName() {return name;}
    public void setGenreName(String name) {this.name=name;}
}
