package com.blogs_apps_api.config;

import com.blogs_apps_api.security.CustomUserDtlService;
import com.blogs_apps_api.security.JWTAuthenticationEntryPoint;
import com.blogs_apps_api.security.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.ldap.EmbeddedLdapServerContextSourceFactoryBean;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true) //Implemented to perform user role based api access hasRole
public class SecurityConfig
        extends WebSecurityConfigurerAdapter {

    public static final String [] PUBLIC_URL = {"/api/v1/auth/**",
            "/v3/api-docs/**", //http://localhost:9090/v3/api-docs
            "/v2/api-docs/**",
            "/swagger-ui/**", //http://localhost:9090/swagger-ui/index.html
            "/swagger-resources/**",
            "/webjars/**"
    };
    @Autowired
    private CustomUserDtlService customUserDtlService;

    @Autowired
    private JWTAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
                http
                .csrf()
                .disable()
                .authorizeRequests()
//                        .antMatchers("/api/v1/auth/login").permitAll()
                        .antMatchers(PUBLIC_URL).permitAll() // Allow all api after api/v1/auth , // Swagger ui url permitted
                        .antMatchers(HttpMethod.GET).permitAll() //Allow all get api without any security
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(this.authenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

                http.addFilterBefore(this.jwtAuthenticationFilter , UsernamePasswordAuthenticationFilter.class);
    }

//            @Bean
//            public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//                http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();
//                return http.build();
//            }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(this.customUserDtlService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
