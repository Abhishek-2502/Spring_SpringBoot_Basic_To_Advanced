package com.example.advancedjpa.demo.repository;

import java.util.UUID;

public interface CustomerRepositoryCustom {
    void softDeleteCustomer(UUID id);
}
