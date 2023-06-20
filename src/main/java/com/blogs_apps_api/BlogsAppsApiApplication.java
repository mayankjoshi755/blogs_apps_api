package com.blogs_apps_api;

import com.blogs_apps_api.payloads.PostsDto;
import com.blogs_apps_api.repositories.PostsRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@SpringBootApplication
public class BlogsAppsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogsAppsApiApplication.class, args);

	}

	@Bean
	public ModelMapper modelMapper()
	{
		return  new ModelMapper();
	}


}
