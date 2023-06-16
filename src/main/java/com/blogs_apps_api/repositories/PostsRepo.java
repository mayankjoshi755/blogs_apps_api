package com.blogs_apps_api.repositories;

import com.blogs_apps_api.entities.Category;
import com.blogs_apps_api.entities.Post;
import com.blogs_apps_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostsRepo extends JpaRepository<Post,Integer> {

    List<Post> findByUser(User user);

    List<Post> findByCategory(Category categories);
}
