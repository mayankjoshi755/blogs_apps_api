package com.blogs_apps_api.security;

import com.blogs_apps_api.entities.User;
import com.blogs_apps_api.globalExceptions.ResourceNotFoundException;
import com.blogs_apps_api.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDtlService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // load username form db by user name.

        User user = this.userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User not found with email id " , "email" , username ));

        return user;
    }
}