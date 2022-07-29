package com.xm.recommendation.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
public class CryptoCurrencyRateDto {
    private Timestamp timeStamp;
    private String cryptoCurrency;
    private BigDecimal price;
}
