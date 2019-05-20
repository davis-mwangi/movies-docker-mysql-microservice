/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.controller;

import com.safaricom.movie.auth.CurrentUser;
import com.safaricom.movie.auth.UserPrincipal;
import com.safaricom.movie.entities.Movie;
import com.safaricom.movie.entities.Rating;
import com.safaricom.movie.entities.Status;
import com.safaricom.movie.entities.Type;
import com.safaricom.movie.payload.MovieRequest;
import com.safaricom.movie.repository.MovieDao;
import com.safaricom.movie.repository.RatingDao;
import com.safaricom.movie.repository.StatusDao;
import com.safaricom.movie.repository.TypeDao;
import com.safaricom.movie.service.MovieService;
import com.safaricom.movie.utils.AppConstants;
import com.safaricom.movie.utils.Response;
import com.safaricom.movie.utils.SingleItemResponse;
import com.safaricom.movie.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author david
 */
@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {
    
    @Autowired
    private MovieService movieService;
    
    @Autowired
    private MovieDao movieDao;
    
    @Autowired
    private TypeDao typeDao;
    
    @Autowired
    private RatingDao ratingDao;
    
    @Autowired
    private StatusDao statusDao;
    
    @PostMapping
    private ResponseEntity createMovie(@RequestBody MovieRequest request, @CurrentUser UserPrincipal currentUser){
        boolean exists = movieDao.existsByTitle(request.getTitle());
        if(exists){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new SingleItemResponse(Response.MOVIE_EXISTS.status(), null));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.createUpdateMovie(request));
    }
    
    @PutMapping
    private ResponseEntity updateMovie(@RequestBody MovieRequest request, @CurrentUser UserPrincipal currentUser){
        boolean exists = movieDao.existsById(request.getId());
        
        if(!exists){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SingleItemResponse(Response.MOVIE_NOT_FOUND.status(), null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(movieService.createUpdateMovie(request));
    }
    
    @GetMapping
    public ResponseEntity getMovies(
            @RequestParam(value = "direction", defaultValue = AppConstants.Pagination.DEFAULT_ORDER_DIRECTION) String direction,
            @RequestParam(value = "oderBy", defaultValue = AppConstants.Pagination.DEFAULT_ORDER_BY) String orderBy,
            @RequestParam(value = "page", defaultValue = AppConstants.Pagination.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.Pagination.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "flag", required = false) Integer watchedFlag,
            @CurrentUser UserPrincipal currentUser) {
        Pageable pageable = Util.getPageable(page, size, direction, orderBy);
        Page<Movie> movies = movieDao.findAllByDateDeletedIsNull(pageable);
        
        if( watchedFlag != null){
            movies = movieDao.findAllByStatusAndDateDeletedIsNull(new Status(watchedFlag), pageable);
        }
        return Util.getResponse(Response.SUCCESS.status(), Util.getResponse(movies, movies.map(movie ->  movie
        ).getContent()));
    }
    
    @DeleteMapping("/{id}")
    private ResponseEntity deleteMovie(@PathVariable("id") Integer id, @CurrentUser UserPrincipal currentUser){
        boolean exists = movieDao.existsById(id);
        
        if(!exists){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SingleItemResponse(Response.MOVIE_NOT_FOUND.status(), null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(movieService.deleteMovie(id));
    }
    @GetMapping("/{id}")
    private ResponseEntity getMovie(@PathVariable("id") Integer id,@CurrentUser UserPrincipal currentUser){
        boolean exists = movieDao.existsById(id);
        
        if(!exists){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SingleItemResponse(Response.MOVIE_NOT_FOUND.status(), null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(movieService.getMovie(id));
    }
    
    
    /// Supplements
    
    @GetMapping("/ratings")
    public ResponseEntity getRatings(
            @RequestParam(value = "direction", defaultValue = AppConstants.Pagination.DEFAULT_ORDER_DIRECTION) String direction,
            @RequestParam(value = "oderBy", defaultValue = AppConstants.Pagination.DEFAULT_ORDER_BY) String orderBy,
            @RequestParam(value = "page", defaultValue = AppConstants.Pagination.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.Pagination.DEFAULT_PAGE_SIZE) int size,
            @CurrentUser UserPrincipal currentUser) {
        Pageable pageable = Util.getPageable(page, size, direction, orderBy);
        Page<Rating> ratings = ratingDao.findAll(pageable);
        return Util.getResponse(Response.SUCCESS.status(), Util.getResponse(ratings, ratings.map(rating ->  rating
        ).getContent()));
    }
    
    @GetMapping("/types")
    public ResponseEntity getTypes(
            @RequestParam(value = "direction", defaultValue = AppConstants.Pagination.DEFAULT_ORDER_DIRECTION) String direction,
            @RequestParam(value = "oderBy", defaultValue = AppConstants.Pagination.DEFAULT_ORDER_BY) String orderBy,
            @RequestParam(value = "page", defaultValue = AppConstants.Pagination.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.Pagination.DEFAULT_PAGE_SIZE) int size) {
        
        Pageable pageable = Util.getPageable(page, size, direction, orderBy);
        Page<Type> types = typeDao.findAll(pageable);
        return Util.getResponse(Response.SUCCESS.status(), Util.getResponse(types, types.map(type ->  type
        ).getContent()));
    }
    
    @GetMapping("/status")
    public ResponseEntity getMovieSatus(
            @RequestParam(value = "direction", defaultValue = AppConstants.Pagination.DEFAULT_ORDER_DIRECTION) String direction,
            @RequestParam(value = "oderBy", defaultValue = AppConstants.Pagination.DEFAULT_ORDER_BY) String orderBy,
            @RequestParam(value = "page", defaultValue = AppConstants.Pagination.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.Pagination.DEFAULT_PAGE_SIZE) int size) {
        
        Pageable pageable = Util.getPageable(page, size, direction, orderBy);
        Page<Status> statuses = statusDao.findAll(pageable);
        return Util.getResponse(Response.SUCCESS.status(), Util.getResponse(statuses, statuses.map(status ->  status
        ).getContent()));
    }
}
