package com.xm.recommendation.controller;

import com.xm.recommendation.dto.CryptoNormalizedRangeDto;
import com.xm.recommendation.dto.CryptoStatisticsDto;
import com.xm.recommendation.facade.CryptoFacade;
import com.xm.recommendation.validator.CryptoValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cryptos")
public class CryptoController {

    private final CryptoFacade cryptoFacade;
    private final CryptoValidator validator;

    @Operation(summary = "Get normalizedRange for all crypto currencies",
            description = "Get normalizedRange for all crypto currencies and time period in days." +
                    "Normalized range = (maxRate-minRate)/minRate")
    @GetMapping("/normalized-rates")
    public List<CryptoNormalizedRangeDto> getAllCryptosNormalizedRates(
            @Parameter(description = "time period in days till now")
            @RequestParam(value = "period", defaultValue = "365") long daysPeriod) {
        return cryptoFacade.getAllCryptosOrderedByNormalizedRange(daysPeriod);
    }

    @Operation(summary = "Get statistics for an crypto currency",
            description = "Get statistics for an crypto currency which include: oldestRecord, newestRecord, recordWithMaxRate, recordWithMinRate")
    @GetMapping("/statistic")
    public CryptoStatisticsDto getStatisticsForCrypto(
            @Parameter(description = "crypto currency code")
            @RequestParam(value = "currency-code") String currencyCode,
            @Parameter(description = "time period in days till now")
            @RequestParam(value = "period", defaultValue = "365") long daysPeriod) {
        validator.validateCurrencyCode(currencyCode);
        return cryptoFacade.getStatisticsForCurrencyCodeAndPeriod(currencyCode, daysPeriod);
    }
}
