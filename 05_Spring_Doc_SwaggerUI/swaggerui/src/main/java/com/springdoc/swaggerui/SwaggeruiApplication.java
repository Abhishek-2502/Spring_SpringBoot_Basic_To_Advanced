package com.springdoc.swaggerui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SwaggeruiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwaggeruiApplication.class, args);
	}

	// To access the Swagger UI and API documentation, visit the following URLs:
	//	http://localhost:8080/swagger-ui.html
	//	http://localhost:8080/swagger-ui/index.html
	//	http://localhost:8080/v3/api-docs
}
