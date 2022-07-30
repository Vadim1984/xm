package com.xm.recommendation.service.impl;

import com.xm.recommendation.dto.CryptoNormalizedRangeDto;
import com.xm.recommendation.exception.CalculationNormalizedRangeException;
import com.xm.recommendation.exception.CryptoRecordNotFoundException;
import com.xm.recommendation.model.CryptoCurrencyModel;
import com.xm.recommendation.model.CryptoRateModel;
import com.xm.recommendation.repository.CryptoCurrencyRepository;
import com.xm.recommendation.repository.CryptoRateRepository;
import com.xm.recommendation.service.CryptoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultCryptoService implements CryptoService {
    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    private final CryptoRateRepository cryptoRateRepository;

    @Override
    public Iterable<CryptoRateModel> getAllCurrencyRates() {
        return cryptoRateRepository.findAll();
    }

    @Override
    public Optional<CryptoCurrencyModel> getCryptoCurrencyByCurrencyCode(String currencyCode) {
        return Optional.ofNullable(cryptoCurrencyRepository.findByCurrencyCode(currencyCode));
    }

    @Override
    public List<CryptoNormalizedRangeDto> getAllCryptosOrderedByNormalizedRange(long monthsPeriod) {
        return StreamSupport.stream(cryptoCurrencyRepository.findAll().spliterator(), false)
                .map(CryptoCurrencyModel::getCurrencyCode)
                .map(currencyCode -> calculateNormalizedRange(currencyCode, monthsPeriod))
                .distinct()
                .sorted(Comparator.comparing(CryptoNormalizedRangeDto::getNormalizedRange))
                .collect(Collectors.toList());
    }

    @Override
    public CryptoRateModel findOldestRateByCurrencyCodeAndPeriod(String currencyCode, long monthsPeriod) {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusMonths(monthsPeriod);

        return cryptoRateRepository.findOldestRateRecordByCurrencyCode(currencyCode, startDate, endDate)
                .orElseThrow(() -> new CryptoRecordNotFoundException(
                        String.format("oldest crypto record not found for currency: [%s], startDate: [%s], endDate: [%s]",
                                currencyCode, startDate, endDate)));
    }

    @Override
    public CryptoRateModel findNewestRateByCurrencyCodeAndPeriod(String currencyCode, long monthsPeriod) {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusMonths(monthsPeriod);

        return cryptoRateRepository.findNewestRateRecordByCurrencyCode(currencyCode, startDate, endDate)
                .orElseThrow(() -> new CryptoRecordNotFoundException(
                        String.format("newest crypto record not found for currency: [%s], startDate: [%s], endDate: [%s]",
                                currencyCode, startDate, endDate)));
    }

    @Override
    public CryptoRateModel findMinRateByCurrencyCodeAndPeriod(String currencyCode, long monthsPeriod) {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusMonths(monthsPeriod);

        return cryptoRateRepository.findRateRecordWithMinRateByCurrencyCode(currencyCode, startDate, endDate)
                .orElseThrow(() -> new CryptoRecordNotFoundException(
                        String.format("crypto record with min rate not found for currency: [%s], startDate: [%s], endDate: [%s]",
                                currencyCode, startDate, endDate)));
    }

    @Override
    public CryptoRateModel findMaxRateByCurrencyCodeAndPeriod(String currencyCode, long monthsPeriod) {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusMonths(monthsPeriod);

        return cryptoRateRepository.findRateRecordWithMaxRateByCurrencyCode(currencyCode, startDate, endDate)
                .orElseThrow(() -> new CryptoRecordNotFoundException(
                        String.format("crypto record with max rate not found for currency: [%s], startDate: [%s], endDate: [%s]",
                                currencyCode, startDate, endDate)));
    }

    private CryptoNormalizedRangeDto calculateNormalizedRange(String currencyCode, long monthsPeriod) {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusMonths(monthsPeriod);
        log.debug("Calculate normalizedRange for currency {} ", currencyCode);
        BigDecimal maxPrice = cryptoRateRepository.findRateRecordWithMaxRateByCurrencyCode(currencyCode, startDate, endDate)
                .map(CryptoRateModel::getPrice)
                .orElseThrow(() -> new CalculationNormalizedRangeException(String.format("Price not found for currency: [%s]", currencyCode)));
        BigDecimal minPrice = cryptoRateRepository.findRateRecordWithMinRateByCurrencyCode(currencyCode, startDate, endDate)
                .map(CryptoRateModel::getPrice)
                .orElseThrow(() -> new CalculationNormalizedRangeException(String.format("Price not found for currency: [%s]", currencyCode)));
        if (minPrice.equals(BigDecimal.ZERO)) {
            throw new CalculationNormalizedRangeException(
                    String.format("Min price iz zero for currency: [%s], not possible to calculate normalizedRange", currencyCode));
        }
        BigDecimal normalizedRange = maxPrice.subtract(minPrice).divide(minPrice, RoundingMode.DOWN);
        log.debug("Calculated values: maxPrice=[{}], minPrice=[{}], normalizedRange=[{}] ", maxPrice, minPrice, normalizedRange);

        return CryptoNormalizedRangeDto.builder()
                .cryptoCurrency(currencyCode)
                .normalizedRange(normalizedRange)
                .build();
    }

}