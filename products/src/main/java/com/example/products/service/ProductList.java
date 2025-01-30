package com.example.products.service;

import com.example.products.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductList {

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Laptop", "High-performance laptop", BigDecimal.valueOf(999.99)));
        products.add(new Product("Smartphone", "Latest model smartphone", BigDecimal.valueOf(799.49)));
        products.add(new Product("Headphones", "Wireless noise-canceling headphones", BigDecimal.valueOf(199.99)));
        products.add(new Product("Keyboard", "Mechanical keyboard with RGB", BigDecimal.valueOf(89.99)));
        products.add(new Product("Mouse", "Ergonomic wireless mouse", BigDecimal.valueOf(49.99)));
        products.add(new Product("Monitor", "4K UHD monitor", BigDecimal.valueOf(299.99)));
        products.add(new Product("Tablet", "Lightweight tablet with pen support", BigDecimal.valueOf(499.99)));
        products.add(new Product("Smartwatch", "Waterproof smartwatch with GPS", BigDecimal.valueOf(149.99)));
        products.add(new Product("Gaming Console", "Next-gen gaming console", BigDecimal.valueOf(499.99)));
        products.add(new Product("External Hard Drive", "1TB portable hard drive", BigDecimal.valueOf(89.99)));
        return products;
    }
}
