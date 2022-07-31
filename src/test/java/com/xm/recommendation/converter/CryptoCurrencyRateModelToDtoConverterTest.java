package com.xm.recommendation.converter;

import com.xm.recommendation.dto.CryptoRateDto;
import com.xm.recommendation.model.CryptoCurrencyModel;
import com.xm.recommendation.model.CryptoRateModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CryptoCurrencyRateModelToDtoConverterTest {

    @InjectMocks
    private CryptoCurrencyRateModelToDtoConverter testInstance;
    @Mock
    private CryptoRateModel cryptoRateModel;
    @Mock
    private CryptoCurrencyModel cryptoCurrencyModel;


    @Test
    void testConvertIsSuccess() {
        BigDecimal price = new BigDecimal("10.01");
        String cryptoCurrencyCode = "BTC";
        LocalDateTime time = LocalDateTime.now();
        when(cryptoRateModel.getPrice()).thenReturn(price);
        when(cryptoRateModel.getCryptoCurrency()).thenReturn(cryptoCurrencyModel);
        when(cryptoRateModel.getTimeStamp()).thenReturn(time);
        when(cryptoCurrencyModel.getCurrencyCode()).thenReturn(cryptoCurrencyCode);

        CryptoRateDto rateDto = testInstance.convert(cryptoRateModel);

        assertThat(rateDto).isNotNull();
        assertThat(rateDto.getCryptoCurrency()).isEqualTo(cryptoCurrencyCode);
        assertThat(rateDto.getPrice()).isEqualTo(price);
        assertThat(rateDto.getTimeStamp()).isEqualTo(time);
    }

    @Test
    void testConvertWillReturnEmptyDto() {
        CryptoRateDto expectedResult = CryptoRateDto.builder().build();

        CryptoRateDto actualResult = testInstance.convert(null);

        assertThat(actualResult)
                .isNotNull()
                .isEqualTo(expectedResult);
    }
}
