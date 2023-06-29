package com.example.payex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class TvShowController {

    @Autowired
    TvShowService tvShowService ;


    @GetMapping("/getAllTvShow")
    public List<TvShow> getAllTvShow(){

        return tvShowService.getListOfAllTvShow() ;
    }
    @PostMapping("/createTvShow")
    public void createTvShow( TvShow tvShow){
        tvShowService.createTvShow(tvShow) ;


    }

   @DeleteMapping("/deleteTvShow/{id}")
    public void deleteTvShow(@PathVariable long id){
        TvShow tvShow = tvShowService.findById(id) ;
        if(tvShow!=null)
            tvShowService.deleteTvShow(tvShow);
    }

}
