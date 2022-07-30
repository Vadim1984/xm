package com.xm.recommendation.facade;

import com.xm.recommendation.dto.CryptoNormalizedRangeDto;
import com.xm.recommendation.dto.CryptoRateDto;
import com.xm.recommendation.dto.CryptoStatisticsDto;

import java.util.List;

public interface CryptoFacade {
    List<CryptoRateDto> getAllCryptoRates();

    /**
     * Return crypto records ordered by normalized range
     * @see com.xm.recommendation.service.CryptoService#getAllCryptosOrderedByNormalizedRange
     *
     * @param daysPeriod time period in days
     * @return List of {@link CryptoNormalizedRangeDto CryptoNormalizedRangeDto}
     */
    List<CryptoNormalizedRangeDto> getAllCryptosOrderedByNormalizedRange(long daysPeriod);

    /**
     * Return statistics for currency and time period
     *
     * @param currencyCode currency
     * @param daysPeriod time period in days
     * @return {@link CryptoStatisticsDto CryptoStatisticsDto}
     */
    CryptoStatisticsDto getStatisticsForCurrencyCodeAndPeriod(String currencyCode, long daysPeriod);
}
