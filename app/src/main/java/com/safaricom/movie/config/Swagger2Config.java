/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.config;


import static com.google.common.collect.Lists.newArrayList;
import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author david
 * Springfox Docket instance provides the primary API configuration with sensible
 * defaults and convenience methods for configuration.
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    
    @Bean
    public Docket api(){
        AuthorizationScope[] authScopes = new AuthorizationScope[1];
        authScopes[0] = new AuthorizationScopeBuilder().scope("global").description("full access").build();
        SecurityReference securityReference = SecurityReference.builder().reference("Authorization-key")
                .scopes(authScopes).build();

        ArrayList<SecurityContext> securityContexts = newArrayList(
                SecurityContext.builder().securityReferences(newArrayList(securityReference)).build());
        
        
        
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.safaricom.movie.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiEndpointsInfo())
                .securitySchemes(Arrays.asList(apiKey()))
                .securityContexts(securityContexts);
                        
    }

    private ApiInfo apiEndpointsInfo() {
       return new ApiInfoBuilder().title("Spring Boot REST API")
            .description("Movies REST API")
            .contact(new Contact("David Mwangi", "www.javaguides.net", "daudimwash@gmail.com"))
            .license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .version("1.0.0")
                .build();
    }
    
    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .clientId("12345")
                .clientSecret("12345")
                .scopeSeparator(" ")
                .useBasicAuthenticationWithAccessCodeGrant(true)
                .build();
    }

    
    

    private ApiKey apiKey() {
        return new ApiKey("Authorization-key", "Authorization", "header");
    }
}
