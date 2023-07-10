package com.example.payex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeasonService {
    @Autowired
    SeasonRepository seasonRepository ;

    public void createSeason(Season season){
        seasonRepository.save(season) ;
    }
    public void deleteSeason(Season season){
        seasonRepository.delete(season);
    }
    public Season findById(long id){
       return  seasonRepository.findById(id).orElse(null) ;
    }
    public List<Season> findAll(){
        return (List<Season>) seasonRepository.findAll();
    }
}
