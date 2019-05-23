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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@Api(value ="Movies/Series Management System", description="Operations pertaining adding, viewing and deleting movies from the")
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
    
    
    @ApiOperation(value = "Add a movie or series")
    @PostMapping
    private ResponseEntity createMovie(@ApiParam(value = "Movie object store in  the database", required = true)@RequestBody MovieRequest request, @CurrentUser UserPrincipal currentUser){
        boolean exists = movieDao.existsByTitle(request.getTitle());
        if(exists){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new SingleItemResponse(Response.MOVIE_EXISTS.status(), null));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.createUpdateMovie(request));
    }
    
     @ApiOperation(value = "Update a movie or series")
    @PutMapping
    private ResponseEntity updateMovie(@ApiParam(value = "Update movie object", required = true)@RequestBody MovieRequest request, @CurrentUser UserPrincipal currentUser){
        boolean exists = movieDao.existsById(request.getId());
        
        if(!exists){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SingleItemResponse(Response.MOVIE_NOT_FOUND.status(), null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(movieService.createUpdateMovie(request));
    }
    
    @ApiOperation(value = "View a list of available movies or series and also fetch according to flag passed", response = List.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved list"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
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
    
    @ApiOperation(value = "Delete a movie or series")
    @DeleteMapping("/{id}")
    private ResponseEntity deleteMovie( @ApiParam(value = "Movie Id from which movie object will delete from database table", required = true) @PathVariable("id") Integer id, @CurrentUser UserPrincipal currentUser){
        boolean exists = movieDao.existsById(id);
        
        if(!exists){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SingleItemResponse(Response.MOVIE_NOT_FOUND.status(), null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(movieService.deleteMovie(id));
    }
    @ApiOperation(value = "Get a movie or series by Id")
    @GetMapping("/{id}")
    private ResponseEntity getMovie(@ApiParam(value = "Movie id from which movie object will retrieve", required = true) @PathVariable("id") Integer id,@CurrentUser UserPrincipal currentUser){
        boolean exists = movieDao.existsById(id);
        
        if(!exists){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SingleItemResponse(Response.MOVIE_NOT_FOUND.status(), null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(movieService.getMovie(id));
    }
    
    
    /// Supplements
    @ApiOperation(value = "View a list of movie/series ratings available", response = List.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved list"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
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
    
    
    @ApiOperation(value = "View a list of genre type(movie or Series)  available", response = List.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved list"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
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
    
    
    @ApiOperation(value = "View a list of movie/series status(Either `watched` or `unwatched`) available", response = List.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved list"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
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
