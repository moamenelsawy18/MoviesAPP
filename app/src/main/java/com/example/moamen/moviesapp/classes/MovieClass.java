package com.example.moamen.moviesapp.classes;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by MoaMeN on 3/24/2016.
 */
public class MovieClass extends RealmObject {


    @PrimaryKey
    private long id;

    private String Panner;
    private String Poster;
    private String Overview;
    private String Relase_Date;
    private double Vote_Avg;
    private String Title;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPanner() {
        return Panner;
    }

    public void setPanner(String panner) {
        Panner = panner;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
    }

    public String getRelase_Date() {
        return Relase_Date;
    }

    public void setRelase_Date(String relase_Date) {
        Relase_Date = relase_Date;
    }

    public double getVote_Avg() {
        return Vote_Avg;
    }

    public void setVote_Avg(double vote_Avg) {
        Vote_Avg = vote_Avg;
    }

    public MovieClass( long id ,String Panner , String Poster , String Overview , String Relase_Date , double Vote_Avg , String Title)
    {
        this.Panner=Panner;
        this.Poster=Poster;
        this.Overview=Overview;
        this.Relase_Date=Relase_Date;
        this.Vote_Avg=Vote_Avg;
        this.Title=Title;
        this.id = id;
    }

    public MovieClass(Long id ,String Poster , String Title)
    {
        this.id = id;
        this.Poster=Poster;
        this.Title=Title;
    }

    public MovieClass()
    {

    }
}
