/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.repository;

import com.safaricom.movie.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author david
 */
public interface  UserDao extends JpaRepository<User, Integer>{
    @Query(value="SELECT u FROM User u WHERE u.id = :id")
    User geUser(@Param("id") Integer id);
    boolean existsByEmail(String email);
    Page<User>findAllByDateDeletedIsNull(Pageable pageable);
    User findByUsernameOrEmail(String username, String email);
}
