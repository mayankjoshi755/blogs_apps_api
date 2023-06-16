package com.blogs_apps_api.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    @Column(name = "title")
    private String categoryTitle;
    @Column(name = "description")
    private String categoryDesc;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

}
