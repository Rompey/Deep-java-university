package com.example.products.controller;

import com.example.products.model.Product;
import com.example.products.service.ProductList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/list")
@RequiredArgsConstructor
public class ProductListController {

    private final ProductList productList;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productList.getProducts();
        return ResponseEntity.ok().body(products);
    }
}
