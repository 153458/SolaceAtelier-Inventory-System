package com.SolaceAtelier.inventory_system.repositories;

import com.SolaceAtelier.inventory_system.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Requirement: Filter by at least two attributes (Brand and Name)
    List<Product> findByBrandContainingAndNameContaining(String brand, String name, Sort sort);
}