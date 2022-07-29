package com.xm.recommendation.controller;

import com.xm.recommendation.dto.CryptoCurrencyNormalizedRange;
import com.xm.recommendation.dto.CryptoCurrencyRateDto;
import com.xm.recommendation.facade.CryptoFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cryptos")
public class CryptoController {

    private final CryptoFacade cryptoFacade;

    @GetMapping
    public List<CryptoCurrencyRateDto> getAllCryptos() {
        return cryptoFacade.getAllCryptoRates();
    }

    @GetMapping("/normalized-rates")
    public List<CryptoCurrencyNormalizedRange> getAllCryptosNormalizedRates() {
        return cryptoFacade.getAllCryptosOrderedByNormalizedRange();
    }
}
