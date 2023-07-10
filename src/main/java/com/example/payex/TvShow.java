package com.example.payex;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TvShow {

    @Id
    private long id ;
    @Column(name="NAME")
    private String name ;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tvShow")
    private List<Season> seasonList;



    @Column(name="RATING")
     private double rating ;
    @Column(name="EPISODECOUNT")

    private int episodeCount ;
    @Column(name="RELEASEDEPISODE")
    private int releasedEpisodeCount ;

    @Column(columnDefinition = "CLOB")
    private String summary;
    @Column(name="Genre")
    @Enumerated(EnumType.STRING)
    private List<Genres> genres ;

    @Column(name= "ShowDay")
    @Enumerated(EnumType.STRING)
    private  List<Showday> showdays ;

    @Override
    public String toString() {
        return "TvShow{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", episodeCount=" + episodeCount +
                ", releasedEpisodeCount=" + releasedEpisodeCount +
                ", summary='" + summary + '\'' +
                ", genres=" + genres +
                ", imdbLink='" + imdbLink + '\'' +
                '}';
    }

    @Column(name="IMDBLINK")
    private String imdbLink;



    public TvShow(int id, String name,List<Season> seasonList, List<Showday> showdays, List<Genres> genres ,double rating, int episodeCount, int releasedEpisodeCount, String summary, String imdbLink) {
        this.id = id;
        this.name = name;
        this.seasonList = new ArrayList<>();
        this.rating = rating;
        this.episodeCount = episodeCount;
        this.genres = genres ;
        this.showdays = showdays ;
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


    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }

    public List<Showday> getShowdays() {
        return showdays;
    }

    public void setShowdays(List<Showday> showdays) {
        this.showdays = showdays;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
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

    public void addSeason(Season season){
        season.setTvShow(this);
        seasonList.add(season);
    }
    public List<Season> getSeasonList(){
        return seasonList ;
    }



}
