package com.security.securitywithjpa.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults()).build();
    }

    // This bean is used to create in-memory users for authentication
    // In a real application, you would typically use a database or another user store to manage users and their roles.
    // Comment UserRepo.java and MyUserDetailsService.java files to use this in-memory user details service instead of the database./
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user1 = User.withDefaultPasswordEncoder().username("user1").password("password").roles("USER").build();
//
//        UserDetails user2 = User.withDefaultPasswordEncoder().username("user2").password("password").roles("ADMIN").build();
//
//        return new InMemoryUserDetailsManager(user1, user2);
//    }


    // This bean is used to create a custom authentication provider to get user details from the database
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }


    /* In DB :

    CREATE DATABASE securitywithjpa;

    CREATE TABLE users (id INT primary key, username VARCHAR(50), password VARCHAR(50));

    USE securitywithjpa;

    INSERT INTO users (id, username, password) VALUES (1, 'user1', 'password1');
    INSERT INTO users (id, username, password) VALUES (2, 'user2', 'password2');

    // To test the application, you can use the following endpoints in Postman
    // GET http://localhost:8080//students

     */

}
