package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        // This will return a default security filter chain that allows all requests without authentication. You can customize it further as needed.
//        return http.build();
//    }


    // This configuration class sets up basic security for the application.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 1. Disable CSRF protection for simplicity.
//        http.csrf(customizer -> customizer.disable());

        // OR (Both are equivalent, but the first one is more readable. All steps can be done using lambda expressions.)
//        Customizer<CsrfConfigurer<HttpSecurity>> custCsrf = new Customizer<CsrfConfigurer<HttpSecurity>>() {
//            @Override
//            public void customize(CsrfConfigurer<HttpSecurity> customizer) {
//                customizer.disable();
//            }
//        };
//        http.csrf(custCsrf);

        // 2. Require authentication for all requests.
//        http.authorizeHttpRequests(request -> request.anyRequest().authenticated());

        // 3. Enable form-based login with default settings.
//        http.formLogin(Customizer.withDefaults());

        // 4. Enable HTTP Basic authentication with default settings. (For Postman)
//        http.httpBasic(Customizer.withDefaults());

        /* 5. Disable session management to use stateless sessions. This means that the server will not create or use HTTP sessions.
              This is useful for REST APIs where each request is independent.
              Every request will generate a new session ID, and the server will not store any session state.
        */
//        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


//        All Steps using Builder Pattern
//        http
//                .csrf(customizer -> customizer.disable())
//                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
//                .formLogin(Customizer.withDefaults())
//                .httpBasic(Customizer.withDefaults());

//        return http.build();

        // All Steps using Builder Pattern and returning a SecurityFilterChain object.
        return http
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
