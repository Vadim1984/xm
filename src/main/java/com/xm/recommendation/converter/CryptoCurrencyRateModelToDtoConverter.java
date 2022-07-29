package com.xm.recommendation.converter;

import com.xm.recommendation.dto.CryptoCurrencyRateDto;
import com.xm.recommendation.model.CryptoRateModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CryptoCurrencyRateModelToDtoConverter implements Converter<CryptoRateModel, CryptoCurrencyRateDto> {
    @Override
    public CryptoCurrencyRateDto convert(CryptoRateModel source) {
        return CryptoCurrencyRateDto.builder()
                .cryptoCurrency(source.getCryptoCurrency().getCurrencyCode())
                .price(source.getPrice())
                .timeStamp(source.getTimeStamp())
                .build();
    }
}
