package com.example.security_with_jpa_bycrpt_jwt.repo;


import com.example.security_with_jpa_bycrpt_jwt.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {

    Users findByUsername(String username);
}
