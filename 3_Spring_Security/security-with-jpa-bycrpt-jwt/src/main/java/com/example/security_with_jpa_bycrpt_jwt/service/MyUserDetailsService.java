package com.example.security_with_jpa_bycrpt_jwt.service;

import com.example.security_with_jpa_bycrpt_jwt.model.UserPrincipal;
import com.example.security_with_jpa_bycrpt_jwt.model.Users;
import com.example.security_with_jpa_bycrpt_jwt.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Logic to load user details from the database or any other source
        Users user = repo.findByUsername(username);
        if (user == null) {
            System.out.println("User not found with username: " + username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new UserPrincipal(user);

    }
}
