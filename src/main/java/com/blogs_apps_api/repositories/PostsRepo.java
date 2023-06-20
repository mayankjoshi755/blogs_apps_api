package com.blogs_apps_api.repositories;

import com.blogs_apps_api.entities.Category;
import com.blogs_apps_api.entities.Post;
import com.blogs_apps_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
public interface PostsRepo extends JpaRepository<Post,Integer> {

    List<Post> findByUser(User user);

    List<Post> findByCategory(Category categories);

    // Search
   List<Post> findByTitleContaining(String keyword);

//   @Query("select p from posts p where p.content like :keyword")
//   List<Post> searchByContent(@Param("keyword") String keyword);

}
