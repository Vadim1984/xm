package com.xm.recommendation.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@Entity(name = "crypto_rate")
public class CryptoRateModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp timeStamp;
    @ManyToOne
    private CryptoCurrencyModel cryptoCurrency;
    private BigDecimal price;
}
