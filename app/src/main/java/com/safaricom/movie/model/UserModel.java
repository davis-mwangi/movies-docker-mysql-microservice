/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.model;

import com.safaricom.movie.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author david
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String physicalAddress;
    private String city;
    private String country;
    
    public static UserModel convert(User user){
        UserModel model = new UserModel();
        model.setId(user.getId());
        model.setFirstName(user.getFirstName());
        model.setLastName(user.getLastName());
        model.setEmail(user.getEmail());
        model.setPhoneNumber(user.getPhoneNumber());
        model.setPhysicalAddress(user.getPhysicalAddress());
        model.setCity(user.getCity());
        model.setCountry(user.getCountry());
        
        return model;
        
        
    }
}
