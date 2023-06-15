package com.blogs_apps_api.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    private String title;

    private String content;

    private String imageName;

    private String addedDate;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Categories categories;

    @ManyToOne
    private User user;


}
