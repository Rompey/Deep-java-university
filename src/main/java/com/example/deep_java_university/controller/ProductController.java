package com.example.deep_java_university.controller;

import com.example.deep_java_university.dto.UriDTO;
import com.example.deep_java_university.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Void> fetchAndSaveProducts(@RequestBody UriDTO uriDTO) {
        productService.fetchAndSaveProducts(uriDTO.uri());
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> downloadReport() throws IOException {
        ByteArrayInputStream inputStream = productService.generateExcelReport();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=products.xlsx");
        headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(inputStream));
    }

    @GetMapping("/view")
    public String showProductPage() {
        return "products";
    }
}
