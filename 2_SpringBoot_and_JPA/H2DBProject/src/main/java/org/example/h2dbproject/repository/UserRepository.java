package org.example.h2dbproject.repository;

import org.example.h2dbproject.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    Users findByEmail(String email);

    @Query(value = "SELECT * FROM Users u WHERE u.email = ?1", nativeQuery = true)
    Users findByEmailNative(String email);

    List<Users> findByName(String name);

    List<Users> findByNameAndEmail(String name, String email);

    @NativeQuery(value = "SELECT * FROM Users u WHERE u.name = ?1 AND u.email = ?2")
    Users findByNameOrEmailNative(String name, String email);
}

