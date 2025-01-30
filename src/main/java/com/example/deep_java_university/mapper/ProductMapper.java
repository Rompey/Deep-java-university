package com.example.deep_java_university.mapper;

import com.example.deep_java_university.dto.ProductImportDTO;
import com.example.deep_java_university.model.Product;

import java.util.UUID;

public interface ProductMapper {

    static Product buildProduct(ProductImportDTO productImportDTO) {
        return Product.builder()
                .uuid(UUID.randomUUID().toString())
                .name(productImportDTO.getName())
                .description(productImportDTO.getDescription())
                .price(productImportDTO.getPrice())
                .priceUAH(productImportDTO.getPriceUAH())
                .build();
    }
}
