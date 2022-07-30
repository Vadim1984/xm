package com.xm.recommendation.controller;

import com.xm.recommendation.dto.CryptoNormalizedRangeDto;
import com.xm.recommendation.dto.CryptoRateDto;
import com.xm.recommendation.dto.CryptoStatisticsDto;
import com.xm.recommendation.facade.CryptoFacade;
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

    @GetMapping
    public List<CryptoRateDto> getAllCryptos() {
        return cryptoFacade.getAllCryptoRates();
    }

    @GetMapping("/normalized-rates")
    public List<CryptoNormalizedRangeDto> getAllCryptosNormalizedRates(@RequestParam(value = "period", defaultValue = "12") long monthsPeriod) {
        return cryptoFacade.getAllCryptosOrderedByNormalizedRange(monthsPeriod);
    }

    @GetMapping("/statistic")
    public CryptoStatisticsDto getStatisticsForCrypto(@RequestParam(value = "currencyCode") String currencyCode,
                                                      @RequestParam(value = "period", defaultValue = "12") long monthsPeriod) {
        return cryptoFacade.getStatisticsForCurrencyCodeAndPeriod(currencyCode, monthsPeriod);
    }
}
