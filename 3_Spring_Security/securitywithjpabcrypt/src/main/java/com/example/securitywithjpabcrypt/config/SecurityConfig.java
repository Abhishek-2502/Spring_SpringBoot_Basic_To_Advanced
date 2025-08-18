package com.example.securitywithjpabcrypt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
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


    // This bean is used to create a custom authentication provider to get user details from the database
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        // 1. Use NoOpPasswordEncoder for no hashed password. It is used for testing purposes only.
//        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());

        // OR

        /* 2. Use BCryptPasswordEncoder for password encoding. It is fetching hashed passwords from the database.
            Use this after registering users with hashed passwords otherwise you will not be able to hit api if you don't have any hashed password.
            It is used for validating user credentials during login.
            BCrypt has a strength parameter (default = 10). Higher values are more secure but slower.
            Always use BCrypt (or Argon2/SCrypt) instead of MD5, SHA1, or plain text storage.
         */

        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));




        provider.setUserDetailsService(userDetailsService);
        return provider;
    }


    /* In DB :

    CREATE DATABASE securitywithjpa;

     */

}
