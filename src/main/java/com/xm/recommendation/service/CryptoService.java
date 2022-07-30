package com.xm.recommendation.service;

import com.xm.recommendation.dto.CryptoNormalizedRangeDto;
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

    List<CryptoNormalizedRangeDto> getAllCryptosOrderedByNormalizedRange(long monthsPeriod);

    /**
     * Return oldest currency rate record
     *
     * @param currencyCode currency
     * @param monthsPeriod time period in months
     * @return {@link CryptoRateModel oldest record}
     */
    CryptoRateModel findOldestRateByCurrencyCodeAndPeriod(String currencyCode, long monthsPeriod);

    /**
     * Return newest currency rate record
     *
     * @param currencyCode currency
     * @param monthsPeriod time period in months
     * @return {@link CryptoRateModel newest record}
     */
    CryptoRateModel findNewestRateByCurrencyCodeAndPeriod(String currencyCode, long monthsPeriod);

    /**
     * Return currency rate record with min rate
     *
     * @param currencyCode currency
     * @param monthsPeriod time period in months
     * @return {@link CryptoRateModel record with min rate}
     */
    CryptoRateModel findMinRateByCurrencyCodeAndPeriod(String currencyCode, long monthsPeriod);

    /**
     * Return currency rate record with max rate
     *
     * @param currencyCode currency
     * @param monthsPeriod time period in months
     * @return {@link CryptoRateModel record with max rate}
     */
    CryptoRateModel findMaxRateByCurrencyCodeAndPeriod(String currencyCode, long monthsPeriod);
}
