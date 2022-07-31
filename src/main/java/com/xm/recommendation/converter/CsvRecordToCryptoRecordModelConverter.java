package com.xm.recommendation.converter;

import com.xm.recommendation.exception.CsvRecordParsingException;
import com.xm.recommendation.model.CryptoRateModel;
import com.xm.recommendation.repository.CryptoCurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.TimeZone;

@RequiredArgsConstructor
@Component
public class CsvRecordToCryptoRecordModelConverter implements Converter<CSVRecord, CryptoRateModel> {

    private static final String TIMESTAMP_FILED = "timestamp";
    private static final String SYMBOL_FIELD = "symbol";
    private static final String PRICE_FIELD = "price";

    private final CryptoCurrencyRepository cryptoCurrencyRepository;

    @Override
    public CryptoRateModel convert(CSVRecord source) {
        return Optional.ofNullable(source)
                .map(this::convertInternal)
                .orElseThrow(() -> new CsvRecordParsingException("Csv file record cannot be null"));
    }

    private CryptoRateModel convertInternal(CSVRecord source) {
        try {
            String timestamp = source.get(TIMESTAMP_FILED);
            String symbol = source.get(SYMBOL_FIELD);
            String price = source.get(PRICE_FIELD);
            LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(timestamp)),
                    TimeZone.getDefault().toZoneId());

            return CryptoRateModel.builder()
                    .timeStamp(dateTime)
                    .cryptoCurrency(cryptoCurrencyRepository.findByCurrencyCode(symbol))
                    .price(new BigDecimal(price))
                    .build();
        } catch (IllegalArgumentException e) {
            throw new CsvRecordParsingException(e.getMessage());
        }
    }
}
