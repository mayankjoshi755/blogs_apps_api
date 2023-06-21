package com.blogs_apps_api.repositories;

import com.blogs_apps_api.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Integer> {
}
