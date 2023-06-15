package com.blogs_apps_api.repositories;

import com.blogs_apps_api.entities.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Categories ,  Integer> {
}
