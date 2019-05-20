/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.service.implementation;

import com.safaricom.movie.entities.User;
import com.safaricom.movie.model.UserModel;
import com.safaricom.movie.payload.UserRequest;
import com.safaricom.movie.repository.LoginResponse;
import com.safaricom.movie.repository.UserDao;
import com.safaricom.movie.service.UserService;
import com.safaricom.movie.utils.Response;
import com.safaricom.movie.utils.SingleItemResponse;
import com.safaricom.movie.utils.Status;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



/**
 *
 * @author david
 */
@Service
public class UserServiceImpl implements UserService{
    Logger log  = LoggerFactory.getLogger(MovieServiceImpl.class);
    
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public SingleItemResponse createUpdateUser(UserRequest request) {
        User user = new User();
        Date  now =  new Date ();
        user.setEmail(request.getEmail());
        if(request.getId() != null){
            user =  userDao.geUser(request.getId());
            user.setDateLastUpdated(now);
            user.setEmail(user.getEmail());
        }
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPhysicalAddress(request.getPhysicalAddress());
        user.setCity(request.getCity());
        user.setCountry(request.getCountry());
        user.setDateCreated(now);
        user.setPassword(encoder.encode(request.getPassword()));
        user.setUsername(request.getUsername());
        
        userDao.save(user);
        return  new SingleItemResponse(Response.SUCCESS.status(),user);
    }

    @Override
    public SingleItemResponse getUser(Integer id) {
       Optional<User>optionalUser =  userDao.findById(id);
        User user = new User();     
        if(optionalUser.isPresent()){
            user =  optionalUser.get();
        }
        return  new SingleItemResponse(Response.SUCCESS.status(),user);
    }

    
     // Soft Delete
    @Override
    public SingleItemResponse deleteUser(Integer id) {
        Optional<User>optionalUser =  userDao.findById(id);
        User user = new User();
        
        if(optionalUser.isPresent()){
            user =  optionalUser.get();
            user.setDateDeleted(new Date());
            userDao.save(user);
        }
        return  new SingleItemResponse(Response.SUCCESS.status(),null);
    }

}
