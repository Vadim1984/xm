package com.xm.recommendation.service;

import com.xm.recommendation.dto.CryptoCurrencyNormalizedRange;
import com.xm.recommendation.model.CryptoCurrencyModel;
import com.xm.recommendation.model.CryptoRateModel;

import java.util.List;
import java.util.Optional;

public interface CryptoService {

    /**
     * Get all crypto-rates
     *
     * @return Iterable of {@link CryptoRateModel CryptoRateModel}
     */
    Iterable<CryptoRateModel> getAllCurrencyRates();

    /**
     * Get CryptoCurrencyModel
     *
     * @param currencyCode input currencyCode
     * @return Optional of {@link CryptoCurrencyModel CryptoCurrencyModel}
     */
    Optional<CryptoCurrencyModel> getCryptoCurrencyByCurrencyCode(String currencyCode);

    List<CryptoCurrencyNormalizedRange> getAllCryptosOrderedByNormalizedRange();
}
