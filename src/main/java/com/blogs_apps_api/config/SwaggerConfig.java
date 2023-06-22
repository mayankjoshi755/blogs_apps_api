package com.blogs_apps_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private ApiKey apiKeys()
    {
        return new ApiKey("JWT",AUTHORIZATION_HEADER,"header");
    }

    private List<springfox.documentation.spi.service.contexts.SecurityContext> securityContextList()
    {
        return Arrays.asList(SecurityContext.builder().securityReferences(securityReferences()).build());
    }

    private List<SecurityReference> securityReferences()
    {
        AuthorizationScope  scopes = new AuthorizationScope("global" ,"access everything");
        return Arrays.asList(new SecurityReference("JWT",new AuthorizationScope[] {scopes})); // returning scope as anonymous array at 0th position

    }
    @Bean
    public Docket api()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .securityContexts(securityContextList())
                .securitySchemes(Arrays.asList(apiKeys()))
                .select()
                .apis(RequestHandlerSelectors
                        .any())
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo getApiInfo() {
        ApiInfo apiInfo = new ApiInfo("Spring Boot application", "This application is devrloped by MJ", "1.0", "Terms of Service",
                new Contact("Mayank Joshi", "https://github.com/mayankjoshi755/blogs_apps_api", "mayankjoshi755@gmail.com"),
                "Licence ...", "Licence URL...", Collections.emptyList());
        return apiInfo;
    }
}
