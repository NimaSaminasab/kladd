package com.example.payex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class SeasonController {
    @Autowired
    SeasonService seasonService ;

    @GetMapping("/getAllSeasons")
    public List<Season > getAllSeasons(){
        return seasonService.findAll() ;
    }
    @PostMapping("/createSeason")
    public void createSeason(Season season){
        seasonService.createSeason(season);
    }
    @DeleteMapping("/deleteSeason")
    public void deleteSeason(Season season){
        seasonService.deleteSeason(season);
    }

}
