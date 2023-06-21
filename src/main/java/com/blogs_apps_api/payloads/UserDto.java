package com.blogs_apps_api.payloads;

import com.blogs_apps_api.entities.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private int Id;
    @NotEmpty
    @Size(min = 4 , message = "User name must me greater than 4 characters.")
    private String name;
    @Email(message = "given email address is not valid.")
    private String email;
    @NotEmpty
    @Size(min = 3 , max = 10 ,  message =  "password must be in between 3 to 10 characters")
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$\n" , message = "Password must be at least 8 characters long and contain letters and digits.")
    private String password;
    @NotNull
    private String about;

    private Set<RoleDto> roles = new HashSet<>();

}
