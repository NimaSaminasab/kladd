package com.example.payex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TvShowService {

    @Autowired
    TvShowRepository tvShowRepository ;

    public TvShow createTvShow(TvShow tvShow){
        return tvShowRepository.save(tvShow) ;
    }
    public void deleteTvShow(TvShow tvShow){
        tvShowRepository.delete(tvShow);
    }
    public List<TvShow> getListOfAllTvShow(){
        return (List<TvShow>) tvShowRepository.findAll();
    }
    public TvShow findById(long id){
        return tvShowRepository.findById(id).orElse(null);
    }


}
