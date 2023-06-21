package com.blogs_apps_api;

import com.blogs_apps_api.config.AppConstants;
import com.blogs_apps_api.entities.Role;
import com.blogs_apps_api.repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BlogsAppsApiApplication implements CommandLineRunner {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogsAppsApiApplication.class, args);

	}

	@Bean
	public ModelMapper modelMapper()
	{
		return  new ModelMapper();
	}


	@Override
	public void run(String... args) throws Exception {
		try {
			Role role = new Role();
			role.setRoleId(AppConstants.NORMAL_USER);
			role.setName("USER");

			Role roleAdmin = new Role();
			roleAdmin.setRoleId(AppConstants.ADMIN_USER);
			roleAdmin.setName("ADMIN");
			List<Role> list = new ArrayList<>();
			list.add(role);
			list.add(roleAdmin);

			List<Role> rolesCreated = this.roleRepo.saveAll(list);

			rolesCreated.forEach(e -> System.out.println(e.getName()));


		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

	}
}
