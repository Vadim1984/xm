package com.xm.recommendation.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Builder
@Entity(name = "crypto_currency")
public class CryptoCurrencyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String currency;
    @OneToMany(mappedBy = "cryptoCurrency")
    private List<CryptoRateModel> currencyRates;
}
