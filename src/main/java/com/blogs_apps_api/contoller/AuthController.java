package com.blogs_apps_api.contoller;

import com.blogs_apps_api.globalExceptions.ApiException;
import com.blogs_apps_api.payloads.JWTAuthRequest;
import com.blogs_apps_api.payloads.JWTAuthResponse;
import com.blogs_apps_api.security.JWTTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private JWTTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> createToken(@RequestBody JWTAuthRequest jwtAuthRequest) throws Exception {
        this.authenticate(jwtAuthRequest.getEmail() , jwtAuthRequest.getPassword());

        UserDetails user = this.userDetailsService.loadUserByUsername(jwtAuthRequest.getEmail());

        String token = this.jwtTokenHelper.generateToken(user);

        JWTAuthResponse response = new JWTAuthResponse();
        response.setToken(token);

        return new ResponseEntity<>(response , HttpStatus.OK);

    }

    private void authenticate(String email, String password) throws ApiException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email,password);
        try {
            this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }
        catch (Exception e) // BADCredentialException
        {
            System.out.println("Exception occurred on authenticate method of AuthController");
            throw new ApiException("Invalid user name or password ...");
        }
    }
}
