package com.example.security_with_jpa_bycrpt_jwt.service;

import com.example.security_with_jpa_bycrpt_jwt.model.Users;
import com.example.security_with_jpa_bycrpt_jwt.repo.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(Users user){
        // Storing the user with an encoded password
        String rawPassword = user.getPassword();
        System.out.println("Registering user: " + user.getUsername());
        System.out.println("Original password: " + rawPassword);

        user.setPassword(encoder.encode(user.getPassword()));

        String hashedPassword = user.getPassword();
        System.out.println("Encoded password: " + hashedPassword);

        // Verify password
        boolean matches = encoder.matches(rawPassword, hashedPassword);
        System.out.println("Password matches: " + matches);

        return repo.save(user);
    }

    public String verify(Users user) {
        Authentication authentication =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getUsername(), user.getPassword()));

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        }
        return "Failed";
    }
}
