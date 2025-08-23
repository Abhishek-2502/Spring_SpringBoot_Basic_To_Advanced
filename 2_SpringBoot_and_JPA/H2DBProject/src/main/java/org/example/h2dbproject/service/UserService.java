package org.example.h2dbproject.service;

import org.example.h2dbproject.entity.Users;
import org.example.h2dbproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    @Autowired
    UserRepository repo;

    public List<Users> getUsers() {
        return repo.findAll();
    }

    public Users getUserByEmail(String email) {
        return repo.findByEmail(email);
    }

    public String addUser(Users users) {
        repo.save(users);
        return "Added " + users+ " successfully";
    }

}
