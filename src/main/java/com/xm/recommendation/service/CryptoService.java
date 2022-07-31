package com.xm.recommendation.service;

import com.xm.recommendation.dto.CryptoNormalizedRangeDto;
import com.xm.recommendation.model.CryptoCurrencyModel;
import com.xm.recommendation.model.CryptoRateModel;

import java.util.List;
import java.util.Optional;

public interface CryptoService {

    /**
     * Get supported CryptoCurrency by currencyCode
     *
     * @param currencyCode currency
     * @return Optional of {@link CryptoCurrencyModel CryptoCurrencyModel}
     */
    Optional<CryptoCurrencyModel> getCryptoCurrencyByCurrencyCode(String currencyCode);

    /**
     * Return crypto records ordered by normalized range.
     * Normalized range = (max-min)/min
     *
     * @param daysPeriod time period in days
     * @return List of {@link CryptoNormalizedRangeDto CryptoNormalizedRangeDto}
     */
    List<CryptoNormalizedRangeDto> getAllCryptosOrderedByNormalizedRange(long daysPeriod);

    /**
     * Return oldest currency rate record
     *
     * @param currencyCode currency
     * @param daysPeriod time period in days
     * @return {@link CryptoRateModel oldest record}
     */
    CryptoRateModel findOldestRateByCurrencyCodeAndPeriod(String currencyCode, long daysPeriod);

    /**
     * Return newest currency rate record
     *
     * @param currencyCode currency
     * @param daysPeriod time period in days
     * @return {@link CryptoRateModel newest record}
     */
    CryptoRateModel findNewestRateByCurrencyCodeAndPeriod(String currencyCode, long daysPeriod);

    /**
     * Return currency rate record with min rate
     *
     * @param currencyCode currency
     * @param daysPeriod time period in days
     * @return {@link CryptoRateModel record with min rate}
     */
    CryptoRateModel findMinRateByCurrencyCodeAndPeriod(String currencyCode, long daysPeriod);

    /**
     * Return currency rate record with max rate
     *
     * @param currencyCode currency
     * @param daysPeriod time period in days
     * @return {@link CryptoRateModel record with max rate}
     */
    CryptoRateModel findMaxRateByCurrencyCodeAndPeriod(String currencyCode, long daysPeriod);
}
