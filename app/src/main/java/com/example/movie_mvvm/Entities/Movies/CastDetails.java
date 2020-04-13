package com.example.movie_mvvm.Entities.Movies;

import com.google.gson.annotations.SerializedName;

public class CastDetails {

    private int id;
    private String name;
    private String biography;
    @SerializedName("profile_path") private String castDetailPoster;
    @SerializedName("known_for_department") private String department;

    public CastDetails(int id, String name, String biography, String castDetailPoster, String department) {
        this.id=id;
        this.name=name;
        this.biography=biography;
        this.castDetailPoster=castDetailPoster;
        this.department=department;
    }

    //id
    public int getCastDetailsId() {return  id;}
    public void setCastDetailsId(int id) {this.id=id;}

    //name
    public String getCastDetailsName() {return name;}
    public void setCastDetailName(String name) {this.name=name;}

    //biography
    public String getCastDetailBiography() {return biography;}
    public void setCastDetailBiography(String biography) {this.biography=biography;}

    //castDetailsPoster
    public String getCastDetailPoster() {return castDetailPoster;}
    public void setCastDetailPoster(String castDetailPoster) {this.castDetailPoster=castDetailPoster;}

    //department
    public String getCastDetailDepartment() {return department;}
    public void setCastDetailDepartment(String department) {this.department=department;}
}
