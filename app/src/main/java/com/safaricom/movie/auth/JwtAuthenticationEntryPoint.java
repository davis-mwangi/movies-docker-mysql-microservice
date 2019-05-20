/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.auth;

import com.safaricom.movie.utils.Status;
import com.safaricom.movie.utils.Util;
import java.io.IOException;
import static java.lang.Math.log;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 *
 * @author david
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger log =  LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
    
    /*
    *This method si called whenever an exception is thrown due to an unauthenticated 
    * user trying to access a resource that requires authentication
    */
    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException) throws IOException {
            log.warn("User: FAILED");

        response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        response.getOutputStream().println(Util.toJson(new Status(403,authException.getMessage())));
    }
    
}
