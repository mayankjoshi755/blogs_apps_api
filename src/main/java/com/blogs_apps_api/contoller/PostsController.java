package com.blogs_apps_api.contoller;

import com.blogs_apps_api.payloads.PostsDto;
import com.blogs_apps_api.services.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private PostsService postsService;
    // Create
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostsDto>  createPost(@RequestBody PostsDto postsDto , @PathVariable Integer userId , @PathVariable Integer categoryId)
    {
        PostsDto createdPost = this.postsService.createPost(postsDto , userId , categoryId);

        return  new ResponseEntity<PostsDto>(createdPost , HttpStatus.CREATED);
    }

    // get
    @GetMapping("/{postId}")
    public ResponseEntity<PostsDto> getPostById (@PathVariable Integer postId )
    {
        return ResponseEntity.ok( this.postsService.getPostById(postId));
    }

    // getAll
    @GetMapping("/")
    public ResponseEntity<List<PostsDto>> getAllPosts ()
    {
        List<PostsDto> postsDtosList =  this.postsService.getAllPost();
        return ResponseEntity.ok(postsDtosList);
    }

    // Get by user

    @GetMapping("/user/{userID}/posts")
    public ResponseEntity<List<PostsDto>> getPostsByUser(@PathVariable Integer userID)
    {
       List<PostsDto> listPosts =  this.postsService.getAllPostByUser(userID);

        return new ResponseEntity<>(listPosts , HttpStatus.OK);
    }

    // Get post by category
    @GetMapping("/category/{categoryID}/posts")
    public ResponseEntity<List<PostsDto>> getPostsByCategory(@PathVariable Integer categoryID)
    {
        List<PostsDto> listPosts =  this.postsService.getAllPostByCategory(categoryID);

        return new ResponseEntity<>(listPosts , HttpStatus.OK);
    }
}