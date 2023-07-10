package com.example.payex;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.List;

@Entity
public class Episode {
    @Id
    private long id ;
    @ManyToOne
    @JoinColumn(name = "SEASON_ID")
    private Season season;

    private String title ;

    public Episode(long id, Season season, String title) {
        this.id = id;
        this.season = season;
        this.title = title;
    }
    public Episode(){}

    public long getId() {
        return id;
    }

    public Season getSeason() {
        return season;
    }

    public String getTitle() {
        return title;
    }
}
