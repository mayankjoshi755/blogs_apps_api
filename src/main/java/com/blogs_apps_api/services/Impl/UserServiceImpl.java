package com.blogs_apps_api.services.Impl;

import com.blogs_apps_api.config.AppConstants;
import com.blogs_apps_api.entities.Role;
import com.blogs_apps_api.entities.User;
import com.blogs_apps_api.globalExceptions.ResourceNotFoundException;
import com.blogs_apps_api.payloads.UserDto;
import com.blogs_apps_api.repositories.RoleRepo;
import com.blogs_apps_api.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.blogs_apps_api.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto registerUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto,User.class);
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));

        // roles set to new user by default
        Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
        user.getRoles().add(role);
       User newUser = this.userRepo.save(user);

        return this.modelMapper.map(newUser , UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        User user = this.dtoToUser(userDto);
        User savedUSer = this.userRepo.save(user);
        return this.userToDto(savedUSer);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","User Id" , userId));

        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setId(userDto.getId());

        User updatedUser = this.userRepo.save(user);
        return this.userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","User Id" , userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> user = this.userRepo.findAll();

       List<UserDto> listOfUserDto = user.stream().map(u -> this.userToDto(u)).collect(Collectors.toList());

        return listOfUserDto;
    }

    @Override
    public void deleteUser(Integer userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","User Id" , userId));
        this.userRepo.delete(user);


    }


    public User dtoToUser(UserDto userDto)
    {
        User user = this.modelMapper.map(userDto, User.class);  // Moidel mapper coverts automatically userDto object to User class object just like below commented code.

        //        User user = new User();
        //        user.setId(userDto.getId());
        //        user.setName(userDto.getName());
        //        user.setEmail(userDto.getEmail());
        //        user.setAbout(userDto.getAbout());
        //        user.setPassword(userDto.getPassword());

        return user;
    }

    public UserDto userToDto(User user)
    {
        UserDto userDto = this.modelMapper.map(user , UserDto.class);

        return userDto;
    }

//    public List<UserDto> userListToDto (List<User> users)
//    {
//        List<UserDto> userDto = new ArrayList<>();
//
//                users.stream().map(this::userToDto)
//            .forEach(userDto::add);
//
//        return userDto;
//    }

}
