package com.example.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

}


// CommandLineRunner is an interface in Spring Boot that can be used to execute code after the application context is loaded and before the application starts.
// It is often used for initialization tasks or to run specific logic at startup.
//@SpringBootApplication
//public class SecurityApplication implements CommandLineRunner {
//
//	public static void main(String[] args) {
//		SpringApplication.run(SecurityApplication.class, args);
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//		System.out.println("This method runs after the application starts.");
//		// You can add any initialization logic here
//		System.out.println("Application is running successfully!");
//	}
//}
