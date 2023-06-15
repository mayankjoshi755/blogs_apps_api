package com.blogs_apps_api.repositories;

import com.blogs_apps_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,  Integer> {


}
