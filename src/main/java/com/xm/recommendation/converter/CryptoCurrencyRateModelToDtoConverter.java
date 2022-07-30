package com.xm.recommendation.converter;

import com.xm.recommendation.dto.CryptoRateDto;
import com.xm.recommendation.model.CryptoCurrencyModel;
import com.xm.recommendation.model.CryptoRateModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CryptoCurrencyRateModelToDtoConverter implements Converter<CryptoRateModel, CryptoRateDto> {
    @Override
    public CryptoRateDto convert(CryptoRateModel source) {
        CryptoRateDto.CryptoRateDtoBuilder builder = CryptoRateDto.builder();
        Optional.ofNullable(source.getCryptoCurrency())
                .map(CryptoCurrencyModel::getCurrencyCode)
                .ifPresent(builder::cryptoCurrency);

        return builder
                .price(source.getPrice())
                .timeStamp(source.getTimeStamp())
                .build();
    }
}
