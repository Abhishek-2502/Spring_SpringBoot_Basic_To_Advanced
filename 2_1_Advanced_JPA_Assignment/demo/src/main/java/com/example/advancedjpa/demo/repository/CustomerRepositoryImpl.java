package com.example.advancedjpa.demo.repository;

import com.example.advancedjpa.demo.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void softDeleteCustomer(UUID id) {
        Customer c = em.find(Customer.class, id);
        if (c != null) {
            c.setDeleted(true);
            em.merge(c);
        }
    }
}
