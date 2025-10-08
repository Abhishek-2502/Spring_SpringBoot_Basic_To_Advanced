package org.example.h2dbproject.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


// BASIC AUTHENTICATION
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeRequests()
//                .requestMatchers(HttpMethod.GET, "/getEmail/*").authenticated()
//                .requestMatchers(HttpMethod.GET,"/admin/**").hasAuthority("ADMIN")
//                .requestMatchers(HttpMethod.GET,"/user/**").hasAuthority("USER")
//                .requestMatchers(HttpMethod.GET,"/product").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()  // Use built-in login form
//                .and()
//                .csrf().disable()
//                .build();
//    }
//
////    @Bean
////    public AuthenticationManager authenticationManager() throws Exception {
////
////    }
//}





import org.example.h2dbproject.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                .requestMatchers(HttpMethod.GET, "/getEmail").authenticated()
                .requestMatchers(HttpMethod.GET, "/admin/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.GET, "/user/**").hasAuthority("ROLE_USER")
                .requestMatchers(HttpMethod.GET, "/guest").permitAll()
                .requestMatchers(HttpMethod.POST, "/register").permitAll()
                .requestMatchers(HttpMethod.PUT, "/edit").hasAnyRole("ROLE_ADMIN", "ROLE_USER")
                .anyRequest().authenticated()
                .and()
                //.formLogin()  //use built-in login page
                .httpBasic()
                .and()
                .authenticationManager(authenticationManager())
                .csrf().disable()
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(daoAuthenticationProvider());
        // If you have multiple authentication methods, you can use:
        // List.of(daoAuthenticationProvider(), customAuthenticationProvider())
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(myUserDetailsService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authenticationProvider;
    }
}
