package com.xm.recommendation.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class CryptoRateDto {
    private LocalDateTime timeStamp;
    private String cryptoCurrency;
    private BigDecimal price;
}
