/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author david
 */
public class Util<T> {
    
    // Converts object to json format
    public static String toJson(Object entity) {
        String json = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    // Converts Json to object
    public T fromJson(String json, Class<T> type) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static ResponseEntity getResponse(Status status, Object entity){
        return ResponseEntity.ok().body(status.getCode() == Response.SUCCESS.status().getCode() ? (entity != null ? entity : new ResultResponse(status)) : new ResultResponse(status));
    }
    public static ResponseEntity getResponse(Status status){
        return getResponse(status,null);
    }
    
    // Sorting the page 
     public static Pageable getPageable(int page, int size,String direction, String orderBy){
        Sort sort;
        if (direction.equals("ASC")) {
            sort = new Sort(Sort.Direction.ASC, orderBy);
        } else {
            sort = new Sort(Sort.Direction.DESC, orderBy);
        }
        return PageRequest.of(page, size, sort);
    }

    // Modify response, if empty list or if not 
    public static PagedResponse getResponse(Page page, List content){
        PagedResponse response;
        if (page.getNumberOfElements() == 0) {
            response = new PagedResponse<>(Response.SUCCESS.status(), Collections.emptyList(), page.getNumber(),
                    page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast());
        } else {
            response = new PagedResponse<>(Response.SUCCESS.status(), content, page.getNumber(),
                    page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast());
        }
        return response;
    }
    
    
    //function to retrieve private key and encode
     public static PrivateKey getPrivateKey(String filename) {
        PrivateKey privateKey = null;
        try {
            byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            privateKey = kf.generatePrivate(spec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return privateKey;
    }
     
     //Retriece public key 
     public static PublicKey getPublicKey(String filename) {
        PublicKey publicKey = null;
        try {
            byte[] keyBytes = Files.readAllBytes(Paths.get(filename));

            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            publicKey = kf.generatePublic(spec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publicKey;
    }
    
}
