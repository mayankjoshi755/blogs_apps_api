package com.blogs_apps_api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
 @Table(name = "citizens")
 @Data
 @AllArgsConstructor
 @NoArgsConstructor
 public class Citizen {

     @Id
     @Column(name="id")
     private Long id;

     @Column(name = "citizen_name")
     private String name;

 }