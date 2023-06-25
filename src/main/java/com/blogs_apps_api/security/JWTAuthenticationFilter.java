package com.blogs_apps_api.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JWTTokenHelper jwtTokenHelper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Get Token .. Authorization is key in header of request which starts with Bearer 213232fsgs.......

        String requestTokenHeader = request.getHeader("Authorization");
        System.out.println("requestToken : " + requestTokenHeader);

        String userName = null;

        // 7 is the index count of Bearer word...6 words and one space
        String token = null;

        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer"))
        {
            try {
                token = requestTokenHeader.substring(7);
                userName = this.jwtTokenHelper.getUserNameFromToken(token);
            }
            catch (IllegalArgumentException ex)
            {
                System.out.println("Unable to get JWT Token");
            }
            catch (ExpiredJwtException ex)
            {
                System.out.println("Token Expired..");
            }catch (MalformedJwtException ex)
            {
                System.out.println("invalid JWT Token");
            }
            catch (Exception ex)
            {
                System.out.println("Other JWT Token ");
                ex.printStackTrace();
            }


        }
        else {
            System.out.println("JWT Token does not starts with Bearer .......");

        }

        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            UserDetails user = this.userDetailsService.loadUserByUsername(userName);
            if(this.jwtTokenHelper.validateToken(token,user))
            {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user , null , user.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }
            else {
                System.out.println("invalid JWT Token ");
            }
        }
        else {
            System.out.println("username is null or context is not null");
        }

        filterChain.doFilter(request,response);

    }
}
