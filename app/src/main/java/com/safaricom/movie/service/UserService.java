/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.service;

import com.safaricom.movie.payload.UserRequest;
import com.safaricom.movie.utils.SingleItemResponse;

/**
 *
 * @author david
 */
public interface UserService {
    public SingleItemResponse createUpdateUser(UserRequest request);
    public SingleItemResponse getUser(Integer id);
    public SingleItemResponse deleteUser(Integer id);
}
