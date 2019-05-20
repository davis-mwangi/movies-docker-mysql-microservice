/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.controller;

import com.safaricom.movie.MovieApplication;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author david
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MovieApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieControllerIT {
    
    @Autowired
     private TestRestTemplate restTemplate;

     @LocalServerPort
     private int port;

     private String getRootUrl() {
         return "http://localhost:" + port + "/api/v1";
     }

    public MovieControllerIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getMovies method, of class MovieController.
     */
    @Test
    public void testGetMovies() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/moviesi",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    /**
     * Test of getRatings method, of class MovieController.
     */
    @Test
    public void testGetRatings() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/movies/ratings",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    /**
     * Test of getTypes method, of class MovieController.
     */
    @Test
    public void testGetTypes() {
       HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/movies/types",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    /**
     * Test of getMovieSatus method, of class MovieController.
     */
    @Test
    public void testGetMovieSatus() {
       HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/movies/status",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

}