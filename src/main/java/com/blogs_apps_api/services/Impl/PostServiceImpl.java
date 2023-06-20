package com.blogs_apps_api.services.Impl;

import com.blogs_apps_api.entities.Category;
import com.blogs_apps_api.entities.Post;
import com.blogs_apps_api.entities.User;
import com.blogs_apps_api.globalExceptions.ResourceNotFoundException;
import com.blogs_apps_api.payloads.PostResponse;
import com.blogs_apps_api.payloads.PostsDto;
import com.blogs_apps_api.repositories.CategoryRepo;
import com.blogs_apps_api.repositories.PostsRepo;
import com.blogs_apps_api.repositories.UserRepo;
import com.blogs_apps_api.services.PostsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.persistence.*;
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

    @PersistenceContext
    private EntityManager entityManager;

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
        Post posts = this.postsRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post" , "post id" , postId));
        this.postsRepo.delete(posts);

    }

    @Override
    public PostResponse getAllPost(Integer pageNumber , Integer pageSize,String sortBy,String sortDir) {

        Sort sort =(sortDir.equals("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = this.postsRepo.findAll(p);
        List<Post> listAllPosts = pagePost.getContent();

        List<PostsDto> listOfPosts = listAllPosts.stream().map(c  -> this.modelMapper.map(c , PostsDto.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setResults(listOfPosts);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setTotalRecords(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public PostsDto getPostById(Integer postId) {
        Post post= this.postsRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","post Id" , postId));
        return this.modelMapper.map(post,PostsDto.class);
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

       List<Post> posts = this.postsRepo.findByTitleContaining(keyword);
      List<PostsDto> postsDtoList = posts.stream().map(p -> this.modelMapper.map(p , PostsDto.class)).collect(Collectors.toList());
        return postsDtoList;
    }
    @Override
    public List<PostsDto> searchPosts(String keyword) {

//        List<Post> posts = this.postsRepo.searchByContent ("%" + keyword + "%");
//        List<PostsDto> postsDtoList = posts.stream().map(p -> this.modelMapper.map(p , PostsDto.class)).collect(Collectors.toList());
//        return postsDtoList;
        return null;
    }


}
