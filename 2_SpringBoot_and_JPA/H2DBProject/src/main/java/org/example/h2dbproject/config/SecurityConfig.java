package org.example.h2dbproject.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                .requestMatchers(HttpMethod.GET, "/getEmail/*").authenticated()
                .requestMatchers(HttpMethod.GET,"/admin/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET,"/user/**").hasAuthority("USER")
                .requestMatchers(HttpMethod.GET,"/product").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()  // Use built-in login form
                .and()
                .csrf().disable()
                .build();
    }

//    @Bean
//    public AuthenticationManager authenticationManager() throws Exception {
//
//    }
}
