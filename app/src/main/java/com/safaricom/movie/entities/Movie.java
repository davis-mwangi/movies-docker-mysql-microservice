/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "All details about the movie/series. ")
public class Movie {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The database generated Movie ID")
    private Integer id;
    
    @ApiModelProperty(notes = "The movie title")
    private String title;
    
    @ApiModelProperty(notes = "The movie description")
    private String description;
    
    @ApiModelProperty(notes = "The movie recommendation")
    private String recommendation;
    
    @ApiModelProperty(notes = "The movie rating")
    @JoinColumn(name = "rating", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Rating rating;
    
    @ApiModelProperty(notes = "The movie type")
    @JoinColumn(name = "type", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Type type;
    
    @ApiModelProperty(notes = "The movie status")
    @JoinColumn(name = "status", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Status status;
    
    @ApiModelProperty(notes = "The date the movie was created")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateCreated;
    
    @ApiModelProperty(notes = "The date the movie was updated")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateLastUpdated;
    
    @ApiModelProperty(notes = "The date the movie was deleted")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateDeleted;
    
}
