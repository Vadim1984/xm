package com.xm.recommendation.facade;

import com.xm.recommendation.dto.CryptoNormalizedRangeDto;
import com.xm.recommendation.dto.CryptoRateDto;

import java.util.List;

public interface CryptoFacade {
    List<CryptoRateDto> getAllCryptoRates();
    List<CryptoNormalizedRangeDto> getAllCryptosOrderedByNormalizedRange(int monthsPeriod);
}
