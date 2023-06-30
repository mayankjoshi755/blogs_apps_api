package com.blogs_apps_api.services.Impl;

import com.blogs_apps_api.config.AppConstants;
import com.blogs_apps_api.entities.Role;
import com.blogs_apps_api.entities.User;
import com.blogs_apps_api.globalExceptions.ResourceNotFoundException;
import com.blogs_apps_api.payloads.RoleDto;
import com.blogs_apps_api.payloads.UserDto;
import com.blogs_apps_api.repositories.RoleRepo;
import com.blogs_apps_api.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.blogs_apps_api.services.UserService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    @Autowired
    JdbcTemplate jdbcTemplate;

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

    @Override
    public void  updateUserRole(RoleDto roleDto) {

        String query = "select user from user_role where user = "+roleDto.getId()+"";
        List<Map<String, Object>> user = jdbcTemplate.queryForList(query);
        System.out.println(user); // [{user=6}]

//       List<Object >list = user.stream().filter(u -> u.containsValue(roleDto.getId())).collect(Collectors.toList());
//
//        if(list.size() > 0)
        if(!user.isEmpty())
        {
            System.out.println("Role exist");
            String stmt = "update user_role set role = ? where user = ?";

            jdbcTemplate.update(stmt,roleDto.getRole(),roleDto.getId());
        }
        else {
            System.out.println("Role does not exist");
            String stmt = "INSERT INTO user_role (`user`,`role`) VALUES ("+roleDto.getId()+","+roleDto.getRole()+")";

            jdbcTemplate.execute(stmt);

        }
    }

    public List<User> customSelectUser(Integer id , String name) {

        return jdbcTemplate.query("SELECT * FROM user where ", new RowMapper<User>() {

            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getInt(id));
                user.setName(rs.getString(name));
                return user;
            }
        });
    }


    public User dtoToUser(UserDto userDto)
    {
        User user = this.modelMapper.map(userDto, User.class);  // Model mapper coverts automatically userDto object to User class object just like below commented code.

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
