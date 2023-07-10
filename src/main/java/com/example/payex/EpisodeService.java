package com.example.payex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EpisodeService {

    @Autowired
    EpisodeRepository episodeRepository ;

    public void createEpisode(Episode episode){
        episodeRepository.save(episode);
    }
    public void deleteEpisode(Episode episode){
        episodeRepository.delete(episode);
    }
    public Episode findEpisodeById(long id){
        return episodeRepository.findById(id).orElse(null) ;
    }
    public List<Episode> findAllEpisode(){
        return (List<Episode>) episodeRepository.findAll();
    }
}
