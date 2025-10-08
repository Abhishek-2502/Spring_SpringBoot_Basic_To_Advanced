package org.example.h2dbproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.example.h2dbproject.entity.MemberPo;

public interface MemberRepo extends JpaRepository<MemberPo, Long> {
    MemberPo findByUsername(String username);

}
