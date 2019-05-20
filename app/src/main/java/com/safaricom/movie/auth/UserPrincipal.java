/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.auth;

import com.safaricom.movie.entities.User;
import com.safaricom.movie.model.UserModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author david
 * Spring Security will use the information stored in UserPricipal Object to perform 
 * authentication and authorization.
 */
public class UserPrincipal implements UserDetails{
    private UserModel user;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    
    public UserModel getUser() {
        return  user;
    }

    public UserPrincipal(UserModel user, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }
    
   

    
    public static UserPrincipal create(User user) {
       List<GrantedAuthority> authorities = new ArrayList<>();
       
       return new UserPrincipal (
                UserModel.convert(user),
                user.getUsername(),
                user.getPassword(),
                authorities      
       );
    }

    @Override
    public String getUsername() {
        return username;
    }

    
    @Override
    public String getPassword() {
        return password;
    }

   
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    @Override
    public boolean isAccountNonExpired() {
       return true;
    }

    @Override
    public boolean isAccountNonLocked() {
         return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
       return true;
    } 
    
}
