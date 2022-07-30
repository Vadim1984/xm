package com.xm.recommendation.converter;

import com.xm.recommendation.dto.CryptoRateDto;
import com.xm.recommendation.model.CryptoRateModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CryptoCurrencyRateModelToDtoConverter implements Converter<CryptoRateModel, CryptoRateDto> {
    @Override
    public CryptoRateDto convert(CryptoRateModel source) {
        return CryptoRateDto.builder()
                .cryptoCurrency(source.getCryptoCurrency().getCurrencyCode())
                .price(source.getPrice())
                .timeStamp(source.getTimeStamp())
                .build();
    }
}
