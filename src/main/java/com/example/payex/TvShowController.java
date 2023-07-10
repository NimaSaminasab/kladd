package com.example.payex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;


@RestController
@CrossOrigin
public class TvShowController {

    @Autowired
    TvShowService tvShowService;


    @GetMapping("/getAllTvShow")
    public List<TvShow> getAllTvShow() {

        return tvShowService.getListOfAllTvShow();
    }

    @PostMapping("/createTvShow")
    public void createTvShow(TvShow tvShow) {
        tvShowService.createTvShow(tvShow);


    }
    @DeleteMapping("/deleteTvShow/{id}")
    public void deleteTvShow(@PathVariable long id) {
        TvShow tvShow = tvShowService.findById(id);
        if (tvShow != null)
            tvShowService.deleteTvShow(tvShow);
    }

    @GetMapping("/findAllByOrderByRatingDesc")
    public List<TvShow> findAllByOrderByRatingDesc() {
        List<TvShow> result = tvShowService.findAllByOrderByRatingDesc();
        print(result);
        return result;
    }

    public void print(List<TvShow> printMe) {
        String filePath = "C:/Users/nimas/Desktop/ratings.txt";
        String output = "";
        for (int i = 0; i < printMe.size(); i++) {
            if (printMe.get(i).getRating() != 0.0) {
                output += String.format("%-40s %s%n", printMe.get(i).getName(), printMe.get(i).getRating());
            }
        }
        try {
            FileWriter writer = new FileWriter(filePath);

            writer.write(output);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
