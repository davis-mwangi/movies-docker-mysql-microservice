/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.config;

import com.safaricom.movie.auth.UserPrincipal;
import com.safaricom.movie.model.UserModel;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author david
 * Thid class automatically updates createdBy and updatedBy fields
 */
@Configuration
@EnableJpaAuditing
public class AuditingConfig {
    
    @Bean 
    public AuditorAware<Integer>auditorProvider(){
        return new SpringSecurityAuditAwareImpl();
    }

    private static class SpringSecurityAuditAwareImpl implements AuditorAware<Integer> {

        public SpringSecurityAuditAwareImpl() {
        }

        @Override
        public Optional<Integer> getCurrentAuditor() {
           Authentication authentication =  SecurityContextHolder.getContext()
                   .getAuthentication();
           
           if(authentication  == null || !authentication.isAuthenticated() ||
                   authentication instanceof AnonymousAuthenticationToken ) {
               return Optional.empty();
           }
           
           UserModel userPrincipal = ((UserPrincipal) authentication.getPrincipal()).getUser();
           
           return Optional.ofNullable(userPrincipal.getId());
        }
    }
    
}
