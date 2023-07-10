package com.example.payex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class EpisodeController {
    @Autowired
    EpisodeService episodeService ;

    @PostMapping("/createEpisode")
    public void createEpisode(Episode episode){
        episodeService.createEpisode(episode);
    }


    @DeleteMapping("/deleteEpisode")
    public void deleteEpisode(Episode episode){
        episodeService.deleteEpisode(episode);
    }
    @GetMapping("/findEpisodeById/{id}")
    public Episode findEpisodeById(@PathVariable long id){
       return episodeService.findEpisodeById(id) ;
    }
    @GetMapping("/getAllEpisodes")
    public List<Episode> getAllEpisodes(){
        return episodeService.findAllEpisode() ;
    }

}
