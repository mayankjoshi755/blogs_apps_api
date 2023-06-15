package com.blogs_apps_api.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Getter
@Setter
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    @Column(name = "title")
    private String categoryName;
    @Column(name = "description")
    private String categoryDesc;

    @OneToMany(mappedBy = "categories" , cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

}
