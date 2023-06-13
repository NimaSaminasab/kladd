package com.example.payex;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TvShow {

    @Id
    private long id ;
    @Column(name="NAME")
    private String name ;

    @Column(name="RATING")
     private float rating ;
    @Column(name="EPISODECOUNT")

    private int episodeCount ;
    @Column(name="RELEASEDEPISODE")
    private int releasedEpisodeCount ;
    @Column(name="SUMMARY")
    private String summary;
    @Column(name="IMDBLINK")
    private String imdbLink;



    public TvShow(long id, String name, float rating, int episodeCount, int releasedEpisodeCount, String summary, String imdbLink) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.episodeCount = episodeCount;
        this.releasedEpisodeCount = releasedEpisodeCount;
        this.summary = summary;
        this.imdbLink = imdbLink;
    }
    public TvShow() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public int getReleasedEpisodeCount() {
        return releasedEpisodeCount;
    }

    public void setReleasedEpisodeCount(int releasedEpisodeCount) {
        this.releasedEpisodeCount = releasedEpisodeCount;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImdbLink() {
        return imdbLink;
    }

    public void setImdbLink(String imdbLink) {
        this.imdbLink = imdbLink;
    }
}
