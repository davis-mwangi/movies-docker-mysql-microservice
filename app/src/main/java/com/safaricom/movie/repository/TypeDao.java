/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.repository;

import com.safaricom.movie.entities.Type;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author david
 */
public interface TypeDao extends JpaRepository<Type ,Integer>{
    
}
