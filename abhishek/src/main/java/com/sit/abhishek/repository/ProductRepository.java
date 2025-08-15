package com.sit.abhishek.repository;




import com.sit.abhishek.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// This interface extends JpaRepository to provide CRUD operations for ProductEntity
// Interface should not have business logic
// JpaRepository provides methods like save, findAll, findById, deleteById, etc.
// The 1st parameter is the entity type, and the 2nd parameter is the type of the entity primary key (in this case, Integer for ProductEntity's id).

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
}
