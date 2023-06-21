package com.blogs_apps_api.security;

import com.blogs_apps_api.config.AppConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTTokenHelper {
    private String secret = "jwtTokenKey";

    public String getUserNameFromToken(String token)
    {
        return getClaimFromToken(token, Claims :: getSubject);
    }

    public Date getExpirtationDateFromToken(String token)
    {
        return getClaimFromToken(token, Claims :: getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims,T> claimResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);

    }
    // Secret key is required for retrieving any information form token

    public Claims getAllClaimsFromToken(String token)
    {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    // To check if token is expired
    public Boolean isTokenExpired(String token)
    {
        final Date expiration = getExpirtationDateFromToken(token);
        return expiration.before(new Date());
    }

    // generate Token
    public String generateToken(UserDetails userDetails)
    {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims,userDetails.getUsername());

    }

    // Creating token

    private String doGenerateToken(Map<String, Object> claims,String subject)
    {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis() + AppConstants.JWT_TOKEN_VALIDITY * 100))
                .setExpiration(new Date(System.currentTimeMillis() + AppConstants.JWT_TOKEN_VALIDITY * 100)).signWith(SignatureAlgorithm.HS512,secret).compact();

    }

    public Boolean validateToken(String token , UserDetails userDetails)
    {
        final String username = getUserNameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
