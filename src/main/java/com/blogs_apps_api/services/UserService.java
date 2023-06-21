package com.blogs_apps_api.services;

import com.blogs_apps_api.payloads.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    UserDto registerUser(UserDto user);
    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user, Integer userId);

    UserDto getUserById(Integer userID);

    List<UserDto> getAllUsers();

    void deleteUser(Integer userId);
}
