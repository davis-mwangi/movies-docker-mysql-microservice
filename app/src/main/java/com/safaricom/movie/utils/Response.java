/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.utils;

/**
 *
 * @author david
 */

public enum Response {
    SUCCESS(0, "Success"),
    NO_USERS_FOUND(1,"No users found"),
    USER_NOT_FOUND(2, "User not found"),
    USER_EXISTS(3, "User already exists"),
    NO_MOVIES_FOUND(4,"No movies/series found"),
    MOVIE_NOT_FOUND(5, "Movie/Series not  found"),
    MOVIE_EXISTS(6, "Movie/Series already exists"),
    FIELD_REQUIRED(7, "Field {0} is required");
    
    private final Status status;
    Response(int code, String message){
        this.status = new Status(code, message);
    }
    
    public Status status(){
        return status;
    }
}
