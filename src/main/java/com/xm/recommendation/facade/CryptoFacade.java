package com.xm.recommendation.facade;

import com.xm.recommendation.dto.CryptoNormalizedRangeDto;
import com.xm.recommendation.dto.CryptoRateDto;
import com.xm.recommendation.dto.CryptoStatisticsDto;

import java.util.List;

public interface CryptoFacade {
    List<CryptoRateDto> getAllCryptoRates();
    List<CryptoNormalizedRangeDto> getAllCryptosOrderedByNormalizedRange(long monthsPeriod);

    /**
     * Return oldest currency rate record
     *
     * @param currencyCode currency
     * @param monthsPeriod time period in months
     * @return {@link CryptoStatisticsDto CryptoStatisticsDto}
     */
    CryptoStatisticsDto getStatisticsForCurrencyCodeAndPeriod(String currencyCode, long monthsPeriod);
}
