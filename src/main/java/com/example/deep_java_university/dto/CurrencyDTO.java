package com.example.deep_java_university.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDTO {
    private String ccy;
    private String base_ccy;
    private BigDecimal buy;
    private BigDecimal sale;
}
