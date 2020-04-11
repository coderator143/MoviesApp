package com.example.movie_mvvm.Entities.Movies;

import java.util.List;

public class MovieCredits {

    private List<MovieCast> cast;
    private List<MovieCrew> crew;

    public MovieCredits (List<MovieCast> cast, List<MovieCrew> crew) {
        this.cast=cast;
        this.crew=crew;
    }

    //cast
    public List<MovieCast> getMovieCast() { return cast; }
    public void setMovieCast(List<MovieCast> cast) { this.cast=cast; }

    //crew
    public List<MovieCrew> getMovieCrew() { return crew; }
    public void setMovieCrew(List<MovieCrew> crew) { this.crew=crew; }
}
