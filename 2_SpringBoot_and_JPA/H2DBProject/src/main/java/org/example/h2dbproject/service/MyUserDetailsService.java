package org.example.h2dbproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.example.h2dbproject.entity.MemberPo;
import org.example.h2dbproject.entity.MyUser;
import org.example.h2dbproject.repository.MemberRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepo memberRepo;

    @Bean
    private BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public MyUser loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberPo memberPo = memberRepo.findByUsername(username);
        return new MyUser(memberPo);
    }

    public ResponseEntity<String> registerUser(MemberPo registrationDto) {
        MemberPo newUser = new MemberPo();
        newUser.setUsername(registrationDto.getUsername());
        newUser.setPassword(passwordEncoder().encode(registrationDto.getPassword()));
        newUser.setEmail(registrationDto.getEmail());
        newUser.setAuthority(registrationDto.getAuthority());
        memberRepo.save(newUser);
        return ResponseEntity.ok("User registered successfully!");
    }
}