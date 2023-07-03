package com.example.payex;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TvShowRepository extends CrudRepository<TvShow, Long> {
    @Query("SELECT t FROM TvShow t ORDER BY t.rating desc ")
    List<TvShow> findAllByOrderByRatingDesc();
}
