package com.blogs_apps_api.contoller;

import com.blogs_apps_api.payloads.ApiResponse;
import com.blogs_apps_api.payloads.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.blogs_apps_api.services.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser (@Valid @RequestBody UserDto userDto)
    {
       UserDto createdUser =  this.userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser (@RequestBody UserDto userDto , @PathVariable Integer userId)
    {
        UserDto updatedUser =  this.userService.updateUser(userDto,userId);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById (@PathVariable Integer userId )
    {
        return ResponseEntity.ok( this.userService.getUserById(userId));
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers ()
    {
        System.out.println("Inside getALLUsers method .......... ");
        return ResponseEntity.ok(this.userService.getAllUsers());

    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser (@PathVariable("userId") Integer userId )
    {
        this.userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse("user deleted successfully" , false), HttpStatus.OK);
    }

}
