package com.blogs_apps_api.contoller;

import com.blogs_apps_api.config.AppConstants;
import com.blogs_apps_api.payloads.ApiResponse;
import com.blogs_apps_api.payloads.PostResponse;
import com.blogs_apps_api.payloads.PostsDto;
import com.blogs_apps_api.services.FileService;
import com.blogs_apps_api.services.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private PostsService postsService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path ;

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
    @GetMapping("")
    public ResponseEntity<PostResponse> getAllPosts ( @RequestParam(value = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                        @RequestParam(value = "pageSize" , defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize ,
                                                      @RequestParam(value = "sortBy" , defaultValue = AppConstants.SORT_BY , required = false) String sortBy,
                                                      @RequestParam(value = "sortDir" , defaultValue = AppConstants.SORT_DIR , required = false) String sortDir)
    {
        PostResponse postResponse =  this.postsService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
        return ResponseEntity.ok(postResponse);
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

    // get post by user
    @DeleteMapping("/{userId}")
    public ResponseEntity deletePostsByUser(@PathVariable Integer userId)
    {
        this.postsService.deletePost(userId);

        return new ResponseEntity (new ApiResponse("Post Deleted Successfully", true), HttpStatus.OK);
    }

    // Update Post
    @PutMapping("/user/{postId}/posts")
    public ResponseEntity<PostsDto>  updatePost(@RequestBody PostsDto postsDto , @PathVariable Integer postId)
    {
        PostsDto updatedPost = this.postsService.updatePost(postsDto , postId);

        return  new ResponseEntity<PostsDto>(updatedPost , HttpStatus.CREATED);
    }

    // Search
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<PostsDto>> searchByTitle(@PathVariable("keyword") String keyword)
    {
       List<PostsDto> posts = this.postsService.searchPost(keyword);

       return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    @GetMapping("/search/content/{keyword}")
    public ResponseEntity<List<PostsDto>> searchByContent(@PathVariable("keyword") String keyword)
    {
        List<PostsDto> posts = this.postsService.searchPost(keyword);

        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    // posts image uploads
    @PostMapping("/image/upload/{postId}")
    public ResponseEntity<PostsDto> uploadPostImage(@RequestParam("image")MultipartFile image,
                                                         @PathVariable Integer postId) throws IOException {
        // will return exception if post id not found
        PostsDto postsDto =this.postsService.getPostById(postId);

        String uploadedImage = this.fileService.uploadImage(path,image);

        postsDto.setImageName(uploadedImage);

        PostsDto postDtoResp = this.postsService.updatePost(postsDto,postId);

        return new ResponseEntity<>(postDtoResp,HttpStatus.OK);
    }

    @GetMapping(value = "/image/download/{imageName}" , produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable String imageName , HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }

}