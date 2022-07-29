package com.xm.recommendation.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Builder
@EqualsAndHashCode
public class CryptoCurrencyNormalizedRange {
    private String cryptoCurrency;
    private BigDecimal normalizedRange;
}
