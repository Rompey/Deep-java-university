package com.example.deep_java_university.repository;

import com.example.deep_java_university.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
