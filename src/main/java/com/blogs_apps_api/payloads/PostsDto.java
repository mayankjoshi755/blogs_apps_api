package com.blogs_apps_api.payloads;

import com.blogs_apps_api.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PostsDto {
    private String title;
    private String content;
    private String imageName;
    private Date addedDate;
    private CategoryDto category;

    private UserDto user;




}
