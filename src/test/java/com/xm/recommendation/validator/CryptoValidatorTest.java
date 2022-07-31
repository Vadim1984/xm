package com.xm.recommendation.validator;

import com.xm.recommendation.exception.CurrencyNotSupportedException;
import com.xm.recommendation.model.CryptoCurrencyModel;
import com.xm.recommendation.service.CryptoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CryptoValidatorTest {

    @InjectMocks
    private CryptoValidator testInstance;
    @Mock
    private CryptoService cryptoService;
    @Mock
    private CryptoCurrencyModel cryptoCurrencyModel;

    @Test
    void testExpectExceptionIfCurrencyIsNotSupported() {
        when(cryptoService.getCryptoCurrencyByCurrencyCode(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> testInstance.validateCurrencyCode("TEST"))
                .isInstanceOf(CurrencyNotSupportedException.class)
                .hasMessage("currency [TEST] is not supported");
    }

    @Test
    void testValidationSuccessForSupportedCurrency() {
        when(cryptoService.getCryptoCurrencyByCurrencyCode(anyString())).thenReturn(Optional.of(cryptoCurrencyModel));

        testInstance.validateCurrencyCode("TEST");
    }
}
