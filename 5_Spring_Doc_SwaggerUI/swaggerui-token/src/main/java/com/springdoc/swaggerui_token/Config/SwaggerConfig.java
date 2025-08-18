package com.springdoc.swaggerui_token.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Demo Application API")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Abhishek Rajput")
                                .email("abhishek25022004@gmail.com"))
                        .termsOfService("http://swagger.io/terms/")
                        .description("This is a sample Spring Boot REST API with Swagger documentation"))

                // Define security scheme for Bearer Authentication
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)      // HTTP Auth
                                        .scheme("bearer")                    // Bearer token
                                        .bearerFormat("JWT")));              // Optional: "JWT"

    }
}
