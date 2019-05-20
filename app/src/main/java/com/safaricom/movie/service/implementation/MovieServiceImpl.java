/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.service.implementation;

import com.safaricom.movie.entities.Movie;
import com.safaricom.movie.entities.Rating;
import com.safaricom.movie.entities.Status;
import com.safaricom.movie.entities.Type;
import com.safaricom.movie.payload.MovieRequest;
import com.safaricom.movie.repository.MovieDao;
import com.safaricom.movie.service.MovieService;
import com.safaricom.movie.utils.Response;
import com.safaricom.movie.utils.SingleItemResponse;
import java.util.Date;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author david
 */
@Service
public class MovieServiceImpl implements MovieService {
    
    Logger log  = LoggerFactory.getLogger(MovieServiceImpl.class);
    
    @Autowired
    private MovieDao  movieDao;

    @Override
    public SingleItemResponse createUpdateMovie(MovieRequest request) {
        Movie movie = new Movie();
        Date now = new Date();
        if(request.getId() != null){
           movie =  movieDao.getMovie(request.getId());
           movie.setDateLastUpdated(now);
        }
        movie.setDateCreated(now);
        movie.setRating(new Rating(request.getRating()));
        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setRecommendation(request.getRecommendation());
        movie.setStatus(new Status(request.getStatus()));
        movie.setType(new Type(request.getType()));
        movieDao.save(movie);
        
        return new SingleItemResponse(Response.SUCCESS.status(), movie);
    }

    @Override
    public SingleItemResponse deleteMovie(Integer id) {
       Optional<Movie> optionalMovie =  movieDao.findById(id);
       Movie movie = new Movie();
       if(optionalMovie.isPresent()){
           movie =  optionalMovie.get();
           movie.setDateDeleted(new Date());
           movieDao.save(movie);
       }
      return new SingleItemResponse(Response.SUCCESS.status(), null);
       
    }

    @Override
    public SingleItemResponse getMovie(Integer id) {
       Optional<Movie> optionalMovie =  movieDao.findById(id);
       Movie movie = new Movie();
       if(optionalMovie.isPresent()){
           movie =  optionalMovie.get();
       }
      return new SingleItemResponse(Response.SUCCESS.status(), movie);
    }
    
}
