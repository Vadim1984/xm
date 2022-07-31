package com.xm.recommendation.facade;

import com.xm.recommendation.converter.CryptoCurrencyRateModelToDtoConverter;
import com.xm.recommendation.dto.CryptoNormalizedRangeDto;
import com.xm.recommendation.dto.CryptoRateDto;
import com.xm.recommendation.dto.CryptoStatisticsDto;
import com.xm.recommendation.facade.impl.DefaultCryptoFacade;
import com.xm.recommendation.model.CryptoRateModel;
import com.xm.recommendation.service.CryptoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultCryptoFacadeTest {

    @InjectMocks
    private DefaultCryptoFacade testInstance;
    @Mock
    private CryptoService cryptoService;
    @Mock
    private CryptoCurrencyRateModelToDtoConverter converter;
    @Mock
    private CryptoRateModel oldestCryptoRecord;
    @Mock
    private CryptoRateModel newestCryptoRecord;
    @Mock
    private CryptoRateModel cryptoRecordWithMaxRate;
    @Mock
    private CryptoRateModel cryptoRecordWithMinRate;

    @Test
    void testGetAllCryptosOrderedByNormalizedRangeWillReturnExpectedList() {
        long daysPeriod = 1L;
        List<CryptoNormalizedRangeDto> expectedCryptoNormalizedRanges = List.of(CryptoNormalizedRangeDto.builder().build());
        when(cryptoService.getAllCryptosOrderedByNormalizedRange(daysPeriod))
                .thenReturn(expectedCryptoNormalizedRanges);

        List<CryptoNormalizedRangeDto> actualExpectedCryptoNormalizedRanges =
                testInstance.getAllCryptosOrderedByNormalizedRange(daysPeriod);

        assertThat(actualExpectedCryptoNormalizedRanges).isEqualTo(expectedCryptoNormalizedRanges);
    }

    @Test
    void testGetStatisticsForCurrencyCodeAndPeriodReturnExpectedDto() {
        String currencyCode = "BTC";
        long daysPeriod = 1L;
        CryptoRateDto oldestCryptoRecordDto = CryptoRateDto.builder().build();
        CryptoRateDto newestCryptoRecordDto = CryptoRateDto.builder().build();
        CryptoRateDto cryptoRecordWithMaxRateDto = CryptoRateDto.builder().build();
        CryptoRateDto cryptoRecordWithMinRateDto = CryptoRateDto.builder().build();
        CryptoStatisticsDto expectedResult = CryptoStatisticsDto.builder()
                .recordWithMinRate(cryptoRecordWithMinRateDto)
                .recordWithMaxRate(cryptoRecordWithMaxRateDto)
                .newestRecord(newestCryptoRecordDto)
                .oldestRecord(oldestCryptoRecordDto)
                .build();
        when(cryptoService.findOldestRateByCurrencyCodeAndPeriod(currencyCode, daysPeriod)).thenReturn(oldestCryptoRecord);
        when(cryptoService.findNewestRateByCurrencyCodeAndPeriod(currencyCode, daysPeriod)).thenReturn(newestCryptoRecord);
        when(cryptoService.findMaxRateByCurrencyCodeAndPeriod(currencyCode, daysPeriod)).thenReturn(cryptoRecordWithMaxRate);
        when(cryptoService.findMinRateByCurrencyCodeAndPeriod(currencyCode, daysPeriod)).thenReturn(cryptoRecordWithMinRate);
        when(converter.convert(oldestCryptoRecord)).thenReturn(oldestCryptoRecordDto);
        when(converter.convert(newestCryptoRecord)).thenReturn(newestCryptoRecordDto);
        when(converter.convert(cryptoRecordWithMaxRate)).thenReturn(cryptoRecordWithMaxRateDto);
        when(converter.convert(cryptoRecordWithMinRate)).thenReturn(cryptoRecordWithMinRateDto);

        CryptoStatisticsDto actualResult = testInstance.getStatisticsForCurrencyCodeAndPeriod(currencyCode, daysPeriod);

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
