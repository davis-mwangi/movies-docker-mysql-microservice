/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.repository;

import com.safaricom.movie.entities.Movie;
import com.safaricom.movie.entities.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author david
 */
public interface MovieDao extends JpaRepository<Movie,Integer>{
    boolean existsByTitle(String title);
    @Query(value="SELECT m FROM Movie m WHERE m.id = :id")
    Movie getMovie(@Param("id") Integer id); 
    
    Page<Movie>findAllByDateDeletedIsNull(Pageable pageable);
    Page<Movie>findAllByStatusAndDateDeletedIsNull(Status status, Pageable pageable);
    
}
