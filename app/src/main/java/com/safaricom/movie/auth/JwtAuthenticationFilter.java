/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.auth;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author david
 * This class gets the JWT token form the request, validate it, load the user 
 * associated with the token, and pass it to the Spring Security
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtTokenProvider tokenProvider;
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
            HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {
        try {
            String jwt =  getJwtFromRequest(request);
            
            if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Integer userId  = tokenProvider.getUserIdFromJWT(jwt);
                
                UserDetails userDetails =  customUserDetailsService.loadUserById(userId);
                UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(userDetails, 
                                null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                
                SecurityContextHolder.getContext().setAuthentication(authentication);                
                
            }
        }catch(Exception ex) {
            logger.error("Could not set user authentication is security context", ex);
        }
        filterChain.doFilter(request, response);
      
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
    
}
