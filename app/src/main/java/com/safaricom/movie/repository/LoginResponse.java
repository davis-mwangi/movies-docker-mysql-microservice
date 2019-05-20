/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.repository;


import com.safaricom.movie.model.UserModel;
import com.safaricom.movie.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author david
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponse {
    private Status status;
    private UserModel user;
}

