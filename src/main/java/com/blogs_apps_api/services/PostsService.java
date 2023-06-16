package com.blogs_apps_api.services;

import com.blogs_apps_api.payloads.PostsDto;

import java.util.List;

public interface PostsService {

    // Create

    PostsDto createPost (PostsDto postsDto , Integer userId , Integer categoryId);

    // update
    PostsDto updatePost(PostsDto postsDto , Integer postId);

    // Delete

    void deletePost(Integer postId);

    // Get All

    List<PostsDto> getAllPost();

    // Get

    PostsDto getPostById(Integer postId);

    // get all post by category
    List<PostsDto> getAllPostByCategory(Integer categoryId);

    // get all post by user
    List<PostsDto> getAllPostByUser (Integer userId);

    // Search
    List<PostsDto> searchPost(String keyword);


}
