/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.auth;

import com.safaricom.movie.entities.User;
import com.safaricom.movie.repository.UserDao;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author david
 * This class loads user's data given its username
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    UserDao userDao;

    /**
     *
     * @param usernameOrEmail
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) 
            throws UsernameNotFoundException {
       // Let people login with either username or email
        User user = userDao.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        return UserPrincipal.create(user);
    }
    
    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Integer id) {
        User user = userDao.findById(id).orElseThrow(
            () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return UserPrincipal.create(user);
    }
    
    
}
