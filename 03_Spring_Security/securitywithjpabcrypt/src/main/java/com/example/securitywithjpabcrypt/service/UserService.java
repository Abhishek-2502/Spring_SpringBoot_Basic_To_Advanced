package com.example.securitywithjpabcrypt.service;

import com.example.securitywithjpabcrypt.model.Users;
import com.example.securitywithjpabcrypt.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

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
}
