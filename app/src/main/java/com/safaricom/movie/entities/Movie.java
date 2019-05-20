/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
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
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private String recommendation;
    
    @JoinColumn(name = "rating", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Rating rating;
    
    @JoinColumn(name = "type", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Type type;
    
    @JoinColumn(name = "status", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Status status;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
   
    private Date dateLastUpdated;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateDeleted;
    
}
