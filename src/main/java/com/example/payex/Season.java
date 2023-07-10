package com.example.payex;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Season {
    @Id
    private long id ;

    @ManyToOne
    @JoinColumn(name = "TV_SHOW_ID")
    private TvShow tvShow;

    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL)
    private List<Episode> episodeList ;

    public Season(long id ) {
        this.id = id;
    }
    public Season(){}

    public long getId(){
        return id ;
    }
    public void addEpisode(Episode episode){
        if(episodeList== null)
        episodeList = new ArrayList<>();

        episodeList.add(episode) ;
    }
    public List<Episode> getEpisodeList(){
        return episodeList ;
    }

    public void setTvShow(TvShow tvShow) {
        this.tvShow = tvShow ;
    }
}
