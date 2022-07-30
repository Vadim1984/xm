package com.xm.recommendation.facade.impl;

import com.xm.recommendation.converter.CryptoCurrencyRateModelToDtoConverter;
import com.xm.recommendation.dto.CryptoNormalizedRangeDto;
import com.xm.recommendation.dto.CryptoRateDto;
import com.xm.recommendation.facade.CryptoFacade;
import com.xm.recommendation.service.CryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class DefaultCryptoFacade implements CryptoFacade {
    private final CryptoService cryptoService;
    private final CryptoCurrencyRateModelToDtoConverter cryptoCurrencyRateModelToDtoConverter;

    public List<CryptoRateDto> getAllCryptoRates() {
        return StreamSupport.stream(cryptoService.getAllCurrencyRates().spliterator(), false)
                .map(cryptoCurrencyRateModelToDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<CryptoNormalizedRangeDto> getAllCryptosOrderedByNormalizedRange(int monthsPeriod) {
        return cryptoService.getAllCryptosOrderedByNormalizedRange(monthsPeriod);
    }

}
