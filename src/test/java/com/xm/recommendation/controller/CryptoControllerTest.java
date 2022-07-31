package com.xm.recommendation.controller;

import com.xm.recommendation.dto.CryptoNormalizedRangeDto;
import com.xm.recommendation.dto.CryptoStatisticsDto;
import com.xm.recommendation.exception.CurrencyNotSupportedException;
import com.xm.recommendation.facade.CryptoFacade;
import com.xm.recommendation.validator.CryptoValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CryptoControllerTest {

    @InjectMocks
    private CryptoController testInstance;
    @Mock
    private CryptoFacade cryptoFacade;
    @Mock
    private CryptoValidator validator;

    @Test
    void testGetAllCryptosNormalizedRatesShouldReturnExpectedResult() {
        long daysPeriod = 1;
        CryptoNormalizedRangeDto normalizedRangeDto = CryptoNormalizedRangeDto.builder().build();
        List<CryptoNormalizedRangeDto> expectedResult = List.of(normalizedRangeDto);
        when(cryptoFacade.getAllCryptosOrderedByNormalizedRange(daysPeriod)).thenReturn(expectedResult);

        List<CryptoNormalizedRangeDto> actualResult = testInstance.getAllCryptosNormalizedRates(daysPeriod);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void testGetStatisticsForCurrencyCodeAndPeriodShouldReturnExpectedResult() {
        long daysPeriod = 1;
        String cryptoCurrencyCode = "BTC";
        CryptoStatisticsDto expectedResult = CryptoStatisticsDto.builder().build();
        when(cryptoFacade.getStatisticsForCurrencyCodeAndPeriod(cryptoCurrencyCode, daysPeriod)).thenReturn(expectedResult);
        doNothing().when(validator).validateCurrencyCode(cryptoCurrencyCode);

        CryptoStatisticsDto actualResult = testInstance.getStatisticsForCrypto(cryptoCurrencyCode, daysPeriod);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void testGetStatisticsForCurrencyCodeAndPeriodWillThrowExceptionForNotSupportedCurrencyCode() {
        long daysPeriod = 1;
        String notSupportedCryptoCurrencyCode = "abc";

        doThrow(new CurrencyNotSupportedException("message")).when(validator).validateCurrencyCode(notSupportedCryptoCurrencyCode);

        assertThatThrownBy(() -> testInstance.getStatisticsForCrypto(notSupportedCryptoCurrencyCode, daysPeriod))
                .isInstanceOf(CurrencyNotSupportedException.class)
                .hasMessage("message");
    }
}
