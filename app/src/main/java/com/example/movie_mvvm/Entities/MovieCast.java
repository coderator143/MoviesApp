package com.example.movie_mvvm.Entities;

import com.google.gson.annotations.SerializedName;

public class MovieCast {

    private int id;
    @SerializedName("name") private String castName;
    @SerializedName("character") private String castCharacter;
    @SerializedName("profile_path") private String castPoster;

    public MovieCast(String castName, String castCharacter, String castPoster, int id) {
        this.id=id;
        this.castName=castName;
        this.castCharacter=castCharacter;
        this.castPoster=castPoster;
    }

    //id
    public int getCastId() { return id; }
    public void setCastId(int id) { this.id=id; }

    //castName
    public String getCastName() { return castName; }
    public void setCastName(String castName) { this.castName=castName; }

    //castCharacter
    public String getCastCharacter() { return castCharacter; }
    public void setCastCharacter(String castCharacter) { this.castCharacter=castCharacter; }

    //castPoster
    public String getCastPoster() { return castPoster; }
    public void setCastPoster(String castPoster) { this.castPoster=castPoster; }
}
