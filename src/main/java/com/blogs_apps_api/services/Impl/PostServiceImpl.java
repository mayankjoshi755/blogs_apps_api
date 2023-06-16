package com.blogs_apps_api.services.Impl;

import com.blogs_apps_api.entities.Category;
import com.blogs_apps_api.entities.Post;
import com.blogs_apps_api.entities.User;
import com.blogs_apps_api.globalExceptions.ResourceNotFoundException;
import com.blogs_apps_api.payloads.CategoryDto;
import com.blogs_apps_api.payloads.PostsDto;
import com.blogs_apps_api.payloads.UserDto;
import com.blogs_apps_api.repositories.CategoryRepo;
import com.blogs_apps_api.repositories.PostsRepo;
import com.blogs_apps_api.repositories.UserRepo;
import com.blogs_apps_api.services.PostsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl  implements PostsService {

    @Autowired
    private PostsRepo postsRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostsDto createPost(PostsDto postsDto, Integer userId , Integer categoryId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User" , "User Id" ,  userId));
        Category categories = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category" , "Category Id" ,  categoryId));

         Post post = this.modelMapper.map(postsDto , Post.class);
         post.setImageName("default.png");
         post.setAddedDate(new Date());
         post.setUser(user);
         post.setCategory(categories);

        Post addedPost =  this.postsRepo.save(post);

        return this.modelMapper.map(addedPost , PostsDto.class);
    }

    @Override
    public PostsDto updatePost(PostsDto postsDto, Integer postId) {

        Post post = this.postsRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Posts" , "Post Id" , postId));

//        post.setCategory(postsDto.getCategory());
        post.setTitle(postsDto.getTitle());
        post.setContent(postsDto.getContent());
//        post.setUser(postsDto.getUser());
        post.setImageName(postsDto.getImageName());
        post.setAddedDate(postsDto.getAddedDate());

        Post updatedPost = this.postsRepo.save(post);

        return this.modelMapper.map(updatedPost , PostsDto.class);
    }

    @Override
    public void deletePost(Integer postId) {

    }

    @Override
    public List<PostsDto> getAllPost() {
        List<PostsDto> listOfPosts = this.postsRepo.findAll().stream().map(c  -> this.modelMapper.map(c , PostsDto.class)).collect(Collectors.toList());
        return listOfPosts;
    }

    @Override
    public PostsDto getPostById(Integer postId) {
        return null;
    }

    @Override
    public List<PostsDto> getAllPostByCategory(Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category" , "category Id" , categoryId));

       List<Post> listOfPost =  this.postsRepo.findByCategory(cat);
       List<PostsDto> listPostDto  =listOfPost.stream().map(post -> this.modelMapper.map(post,PostsDto.class)).collect(Collectors.toList());

        return listPostDto;
    }

    @Override
    public List<PostsDto> getAllPostByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User" , "user Id" , userId));

        List<Post> listOfPost =  this.postsRepo.findByUser(user);
        List<PostsDto> listPostDto  =listOfPost.stream().map(post -> this.modelMapper.map(post,PostsDto.class)).collect(Collectors.toList());

        return listPostDto;
    }

    @Override
    public List<PostsDto> searchPost(String keyword) {
        return null;
    }
}
