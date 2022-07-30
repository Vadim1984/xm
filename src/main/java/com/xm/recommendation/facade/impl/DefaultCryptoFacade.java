package com.xm.recommendation.facade.impl;

import com.xm.recommendation.converter.CryptoCurrencyRateModelToDtoConverter;
import com.xm.recommendation.dto.CryptoNormalizedRangeDto;
import com.xm.recommendation.dto.CryptoRateDto;
import com.xm.recommendation.dto.CryptoStatisticsDto;
import com.xm.recommendation.facade.CryptoFacade;
import com.xm.recommendation.model.CryptoRateModel;
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
    private final CryptoCurrencyRateModelToDtoConverter converter;

    public List<CryptoRateDto> getAllCryptoRates() {
        return StreamSupport.stream(cryptoService.getAllCurrencyRates().spliterator(), false)
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<CryptoNormalizedRangeDto> getAllCryptosOrderedByNormalizedRange(long daysPeriod) {
        return cryptoService.getAllCryptosOrderedByNormalizedRange(daysPeriod);
    }

    @Override
    public CryptoStatisticsDto getStatisticsForCurrencyCodeAndPeriod(String currencyCode, long daysPeriod) {
        CryptoRateModel oldestCryptoRecord = cryptoService.findOldestRateByCurrencyCodeAndPeriod(currencyCode, daysPeriod);
        CryptoRateModel newestCryptoRecord = cryptoService.findNewestRateByCurrencyCodeAndPeriod(currencyCode, daysPeriod);
        CryptoRateModel cryptoRecordWithMaxRate = cryptoService.findMaxRateByCurrencyCodeAndPeriod(currencyCode, daysPeriod);
        CryptoRateModel cryptoRecordWithMinRate = cryptoService.findMinRateByCurrencyCodeAndPeriod(currencyCode, daysPeriod);

        return CryptoStatisticsDto.builder()
                .newestRecord(converter.convert(newestCryptoRecord))
                .oldestRecord(converter.convert(oldestCryptoRecord))
                .recordWithMaxRate(converter.convert(cryptoRecordWithMaxRate))
                .recordWithMinRate(converter.convert(cryptoRecordWithMinRate))
                .build();
    }
}
