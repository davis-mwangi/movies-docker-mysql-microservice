/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.service;

import com.safaricom.movie.payload.MovieRequest;
import com.safaricom.movie.utils.SingleItemResponse;

/**
 *
 * @author david
 */
public interface MovieService {
    public SingleItemResponse createUpdateMovie(MovieRequest request);
    public SingleItemResponse getMovie(Integer id);
    public SingleItemResponse deleteMovie(Integer id);
}
