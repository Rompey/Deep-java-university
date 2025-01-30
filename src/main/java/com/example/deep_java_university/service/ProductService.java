package com.example.deep_java_university.service;

import com.example.deep_java_university.dto.CurrencyDTO;
import com.example.deep_java_university.dto.ProductImportDTO;
import com.example.deep_java_university.mapper.ProductMapper;
import com.example.deep_java_university.model.Product;
import com.example.deep_java_university.repository.ProductRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void fetchAndSaveProducts(String uri) {
        List<ProductImportDTO> productsBlock = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ProductImportDTO>>() {
                })
                .block();

        BigDecimal convertToUAH = fetchDollarRate(new BigDecimal(1));

        if (productsBlock != null) {
            List<ProductImportDTO> productImportDTOS = productsBlock.stream()
                    .map(product -> {
                        product.setPriceUAH(product.getPrice().multiply(convertToUAH));
                        return product;
                    }).toList();

            List<Product> products = productImportDTOS.stream()
                    .map(ProductMapper::buildProduct).toList();

            productRepository.saveAll(products);
        }
    }

    @SneakyThrows
    private BigDecimal fetchDollarRate(BigDecimal price) {
        HttpURLConnection connection = (HttpURLConnection) new URL("https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5").openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder jsonResponse = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonResponse.append(line);
        }
        reader.close();

        List<CurrencyDTO> currencies = objectMapper.readValue(jsonResponse.toString(), new TypeReference<>() {
        });

        CurrencyDTO usdCurrency = currencies.stream()
                .filter(currency -> "USD".equals(currency.getCcy()))
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);

        return price.multiply(usdCurrency.getBuy());
    }


    public ByteArrayInputStream generateExcelReport() throws IOException {
        List<Product> products = productRepository.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Products");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Name");
        header.createCell(1).setCellValue("Description");
        header.createCell(2).setCellValue("Price");
        header.createCell(3).setCellValue("Price_UAH");

        int rowNum = 1;
        for (Product product : products) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(product.getName());
            row.createCell(1).setCellValue(product.getDescription());
            row.createCell(2).setCellValue(product.getPrice().doubleValue());
            row.createCell(3).setCellValue(product.getPriceUAH().doubleValue());
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
}
