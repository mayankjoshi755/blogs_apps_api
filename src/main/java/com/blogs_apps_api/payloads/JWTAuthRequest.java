package com.blogs_apps_api.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWTAuthRequest {
    private String email;
    private String password;
}
