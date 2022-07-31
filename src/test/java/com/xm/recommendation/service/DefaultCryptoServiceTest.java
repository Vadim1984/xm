package com.xm.recommendation.service;

import com.xm.recommendation.dto.CryptoNormalizedRangeDto;
import com.xm.recommendation.exception.CalculationNormalizedRangeException;
import com.xm.recommendation.exception.CryptoRecordNotFoundException;
import com.xm.recommendation.model.CryptoCurrencyModel;
import com.xm.recommendation.model.CryptoRateModel;
import com.xm.recommendation.repository.CryptoCurrencyRepository;
import com.xm.recommendation.repository.CryptoRateRepository;
import com.xm.recommendation.service.impl.DefaultCryptoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultCryptoServiceTest {

    @InjectMocks
    private DefaultCryptoService testInstance;
    @Mock
    private CryptoCurrencyRepository cryptoCurrencyRepository;
    @Mock
    private CryptoRateRepository cryptoRateRepository;
    @Mock
    private CryptoCurrencyModel cryptoCurrencyModelOne;
    @Mock
    private CryptoCurrencyModel cryptoCurrencyModelTwo;
    @Mock
    private CryptoRateModel cryptoRateModelOne;
    @Mock
    private CryptoRateModel cryptoRateModelTwo;
    @Mock
    private CryptoRateModel cryptoRateModelThree;
    @Mock
    private CryptoRateModel cryptoRateModelFour;


    @Test
    void testGetCryptoCurrencyByCurrencyCodeWillReturnOptionalEmpty() {
        when(cryptoCurrencyRepository.findByCurrencyCode(anyString())).thenReturn(null);

        Optional<CryptoCurrencyModel> actualResult = testInstance.getCryptoCurrencyByCurrencyCode("TEST");

        assertThat(actualResult).isEmpty();
    }

    @Test
    void testGetCryptoCurrencyByCurrencyCodeWillReturnOptionalWithObject() {
        when(cryptoCurrencyRepository.findByCurrencyCode(anyString())).thenReturn(cryptoCurrencyModelOne);

        Optional<CryptoCurrencyModel> actualResult = testInstance.getCryptoCurrencyByCurrencyCode("TEST");

        assertThat(actualResult).isNotEmpty()
                .hasValue(cryptoCurrencyModelOne);
    }

    @Test
    void testGetAllCryptosOrderedByNormalizedRangeReturnOrderedListByNormalizedRate() {
        when(cryptoCurrencyRepository.findAll()).thenReturn(List.of(cryptoCurrencyModelOne, cryptoCurrencyModelTwo));
        when(cryptoCurrencyModelOne.getCurrencyCode()).thenReturn("BTC");
        when(cryptoCurrencyModelTwo.getCurrencyCode()).thenReturn("ETH");
        when(cryptoRateRepository.findRateRecordWithMinRateByCurrencyCode(eq("BTC"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Optional.of(cryptoRateModelOne));
        when(cryptoRateRepository.findRateRecordWithMaxRateByCurrencyCode(eq("BTC"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Optional.of(cryptoRateModelTwo));
        when(cryptoRateModelOne.getPrice()).thenReturn(new BigDecimal("11.12"));
        when(cryptoRateModelTwo.getPrice()).thenReturn(new BigDecimal("20.12"));
        when(cryptoRateRepository.findRateRecordWithMinRateByCurrencyCode(eq("ETH"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Optional.of(cryptoRateModelThree));
        when(cryptoRateRepository.findRateRecordWithMaxRateByCurrencyCode(eq("ETH"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Optional.of(cryptoRateModelFour));
        when(cryptoRateModelThree.getPrice()).thenReturn(new BigDecimal("11.12"));
        when(cryptoRateModelFour.getPrice()).thenReturn(new BigDecimal("12.12"));
        CryptoNormalizedRangeDto expectedCryptoNormalizedRangeDtoOne = CryptoNormalizedRangeDto.builder()
                .cryptoCurrency("ETH")
                .normalizedRange(new BigDecimal("0.08"))
                .build();
        CryptoNormalizedRangeDto expectedCryptoNormalizedRangeDtoTwo = CryptoNormalizedRangeDto.builder()
                .cryptoCurrency("BTC")
                .normalizedRange(new BigDecimal("0.80"))
                .build();

        List<CryptoNormalizedRangeDto> normalizedRatesList = testInstance.getAllCryptosOrderedByNormalizedRange(1);

        assertThat(normalizedRatesList).isNotEmpty()
                .containsAll(List.of(expectedCryptoNormalizedRangeDtoOne, expectedCryptoNormalizedRangeDtoTwo));
    }

    @Test
    void testGetAllCryptosOrderedByNormalizedRangeThrowExceptionIfMinPriceIsZero() {
        when(cryptoCurrencyRepository.findAll()).thenReturn(List.of(cryptoCurrencyModelOne));
        when(cryptoCurrencyModelOne.getCurrencyCode()).thenReturn("BTC");
        when(cryptoRateRepository.findRateRecordWithMinRateByCurrencyCode(eq("BTC"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Optional.of(cryptoRateModelOne));
        when(cryptoRateRepository.findRateRecordWithMaxRateByCurrencyCode(eq("BTC"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Optional.of(cryptoRateModelTwo));
        when(cryptoRateModelOne.getPrice()).thenReturn(new BigDecimal("00.00"));
        when(cryptoRateModelTwo.getPrice()).thenReturn(new BigDecimal("20.12"));

        assertThatThrownBy(() -> testInstance.getAllCryptosOrderedByNormalizedRange(1))
                .isInstanceOf(CalculationNormalizedRangeException.class)
                .hasMessage("Min price iz zero for currency: [BTC], not possible to calculate normalizedRange");
    }

    @Test
    void testGetAllCryptosOrderedByNormalizedRangeThrowExceptionIfMaxPriceIsNotFound() {
        when(cryptoCurrencyRepository.findAll()).thenReturn(List.of(cryptoCurrencyModelOne));
        when(cryptoCurrencyModelOne.getCurrencyCode()).thenReturn("BTC");
        when(cryptoRateRepository.findRateRecordWithMaxRateByCurrencyCode(eq("BTC"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> testInstance.getAllCryptosOrderedByNormalizedRange(1))
                .isInstanceOf(CalculationNormalizedRangeException.class)
                .hasMessage("Max price not found for currency: [BTC]");
    }

    @Test
    void testGetAllCryptosOrderedByNormalizedRangeThrowExceptionIfMinPriceIsNotFound() {
        when(cryptoCurrencyRepository.findAll()).thenReturn(List.of(cryptoCurrencyModelOne));
        when(cryptoCurrencyModelOne.getCurrencyCode()).thenReturn("BTC");
        when(cryptoRateRepository.findRateRecordWithMaxRateByCurrencyCode(eq("BTC"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Optional.of(cryptoRateModelTwo));
        when(cryptoRateModelTwo.getPrice()).thenReturn(new BigDecimal("20.12"));
        when(cryptoRateRepository.findRateRecordWithMinRateByCurrencyCode(eq("BTC"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> testInstance.getAllCryptosOrderedByNormalizedRange(1))
                .isInstanceOf(CalculationNormalizedRangeException.class)
                .hasMessage("Min price not found for currency: [BTC]");
    }

    @Test
    void testFindOldestRateByCurrencyCodeAndPeriodReturnExpectedResponse() {
        when(cryptoRateRepository.findOldestRateRecordByCurrencyCode(eq("BTC"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Optional.of(cryptoRateModelOne));

        CryptoRateModel cryptoRate = testInstance.findOldestRateByCurrencyCodeAndPeriod("BTC", 1);

        assertThat(cryptoRate).isEqualTo(cryptoRateModelOne);
    }

    @Test
    void testFindOldestRateByCurrencyCodeAndPeriodThrowExceptionWhenOldestRateIsNotFound() {
        when(cryptoRateRepository.findOldestRateRecordByCurrencyCode(eq("BTC"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> testInstance.findOldestRateByCurrencyCodeAndPeriod("BTC", 1))
                .isInstanceOf(CryptoRecordNotFoundException.class)
                .hasMessageContaining("oldest crypto record not found for currency: [BTC]");
    }

    @Test
    void testFindNewestRateByCurrencyCodeAndPeriodReturnExpectedResponse() {
        when(cryptoRateRepository.findNewestRateRecordByCurrencyCode(eq("BTC"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Optional.of(cryptoRateModelOne));

        CryptoRateModel cryptoRate = testInstance.findNewestRateByCurrencyCodeAndPeriod("BTC", 1);

        assertThat(cryptoRate).isEqualTo(cryptoRateModelOne);
    }

    @Test
    void testFindNewestRateByCurrencyCodeAndPeriodThrowExceptionWhenOldestRateIsNotFound() {
        when(cryptoRateRepository.findNewestRateRecordByCurrencyCode(eq("BTC"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> testInstance.findNewestRateByCurrencyCodeAndPeriod("BTC", 1))
                .isInstanceOf(CryptoRecordNotFoundException.class)
                .hasMessageContaining("newest crypto record not found for currency: [BTC]");
    }

    @Test
    void testFindMinRateByCurrencyCodeAndPeriodReturnExpectedResponse() {
        when(cryptoRateRepository.findRateRecordWithMinRateByCurrencyCode(eq("BTC"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Optional.of(cryptoRateModelOne));

        CryptoRateModel cryptoRate = testInstance.findMinRateByCurrencyCodeAndPeriod("BTC", 1);

        assertThat(cryptoRate).isEqualTo(cryptoRateModelOne);
    }

    @Test
    void testFindMinRateByCurrencyCodeAndPeriodThrowExceptionWhenOldestRateIsNotFound() {
        when(cryptoRateRepository.findRateRecordWithMinRateByCurrencyCode(eq("BTC"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> testInstance.findMinRateByCurrencyCodeAndPeriod("BTC", 1))
                .isInstanceOf(CryptoRecordNotFoundException.class)
                .hasMessageContaining("crypto record with min rate not found for currency: [BTC]");
    }

    @Test
    void testFindMaxRateByCurrencyCodeAndPeriodReturnExpectedResponse() {
        when(cryptoRateRepository.findRateRecordWithMaxRateByCurrencyCode(eq("BTC"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Optional.of(cryptoRateModelOne));

        CryptoRateModel cryptoRate = testInstance.findMaxRateByCurrencyCodeAndPeriod("BTC", 1);

        assertThat(cryptoRate).isEqualTo(cryptoRateModelOne);
    }

    @Test
    void testFindMaxRateByCurrencyCodeAndPeriodThrowExceptionWhenOldestRateIsNotFound() {
        when(cryptoRateRepository.findRateRecordWithMaxRateByCurrencyCode(eq("BTC"), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> testInstance.findMaxRateByCurrencyCodeAndPeriod("BTC", 1))
                .isInstanceOf(CryptoRecordNotFoundException.class)
                .hasMessageContaining("crypto record with max rate not found for currency: [BTC]");
    }
}
