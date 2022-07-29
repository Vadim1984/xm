package com.xm.recommendation.validator;

import com.xm.recommendation.exception.CurrencyNotSupportedException;
import com.xm.recommendation.service.CryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CryptoValidator {
    private final CryptoService cryptoService;

    public void validateCurrencyCode(String currencyCode) {
        cryptoService.getCryptoCurrencyByCurrencyCode(currencyCode)
                .orElseThrow(() -> new CurrencyNotSupportedException(String.format("currency [%s] is not supported", currencyCode)));
    }

}
