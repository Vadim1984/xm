package com.xm.recommendation.facade;

import com.xm.recommendation.dto.CryptoCurrencyRateDto;

import java.util.List;

public interface CryptoFacade {
    List<CryptoCurrencyRateDto> getAllCryptoRates();
}
