/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.controller;

import com.david.poll.payload.JwtAuthenticationResponse;
import com.safaricom.movie.auth.JwtTokenProvider;
import com.safaricom.movie.payload.LoginRequest;
import com.safaricom.movie.entities.User;
import com.safaricom.movie.payload.UserRequest;
import com.safaricom.movie.repository.UserDao;
import com.safaricom.movie.service.UserService;
import com.safaricom.movie.utils.AppConstants;
import com.safaricom.movie.utils.Response;
import com.safaricom.movie.utils.SingleItemResponse;
import com.safaricom.movie.utils.Util;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@RequestMapping("/api/v1/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserDao userDao;

    
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }
    
    @PostMapping("/signup")
    private ResponseEntity createUser(@RequestBody UserRequest request){
        boolean exists = userDao.existsByEmail(request.getEmail());
        
        if(exists){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new SingleItemResponse(Response.USER_EXISTS.status(), null));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUpdateUser(request));
    }
    
    @PutMapping
    private ResponseEntity updateUser(@RequestBody UserRequest request){
        boolean exists = userDao.existsById(request.getId());
        
        if(!exists){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SingleItemResponse(Response.USER_NOT_FOUND.status(), null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.createUpdateUser(request));
    }
    
    @GetMapping
    public ResponseEntity getUsers(
            @RequestParam(value = "direction", defaultValue = AppConstants.Pagination.DEFAULT_ORDER_DIRECTION) String direction,
            @RequestParam(value = "oderBy", defaultValue = AppConstants.Pagination.DEFAULT_ORDER_BY) String orderBy,
            @RequestParam(value = "page", defaultValue = AppConstants.Pagination.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.Pagination.DEFAULT_PAGE_SIZE) int size) {
       
        Page<User> users = userDao.findAllByDateDeletedIsNull(
                Util.getPageable(page, size, direction, orderBy));

        return Util.getResponse(Response.SUCCESS.status(), Util.getResponse(users, users.map(user ->  user
        ).getContent()));
    }
    
    @DeleteMapping("/{id}")
    private ResponseEntity deleteUser(@PathVariable("id") Integer id){
        boolean exists = userDao.existsById(id);
        
        if(!exists){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SingleItemResponse(Response.USER_NOT_FOUND.status(), null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(id));
    }
    @GetMapping("/{id}")
    private ResponseEntity getUser(@PathVariable("id") Integer id){
        boolean exists = userDao.existsById(id);
        
        if(!exists){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SingleItemResponse(Response.USER_NOT_FOUND.status(), null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(id));
    }
    
}
