package com.blogs_apps_api.payloads;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Integer categoryId;
    @NotBlank
    @Size(min = 4 , message = "Name should be at least 4 letters")
    private  String categoryName;

    @NotBlank
    @Size(min = 4, message = "Name should be at least 4 letters")
    private  String categoryDesc;
}
