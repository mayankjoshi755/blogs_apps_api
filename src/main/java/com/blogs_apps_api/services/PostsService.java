package com.blogs_apps_api.services;

import com.blogs_apps_api.payloads.PostResponse;
import com.blogs_apps_api.payloads.PostsDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostsService {

    // Create

    PostsDto createPost (PostsDto postsDto , Integer userId , Integer categoryId);

    // update
    PostsDto updatePost(PostsDto postsDto , Integer postId);

    // Delete

    void deletePost(Integer postId);

    // Get All

    PostResponse getAllPost(Integer pageNumber , Integer pageSize,String sortBy,String sortDir);

    // Get

    PostsDto getPostById(Integer postId);

    // get all post by category
    List<PostsDto> getAllPostByCategory(Integer categoryId);

    // get all post by user
    List<PostsDto> getAllPostByUser (Integer userId);

    List<PostsDto> searchPost(String keyword);


}
