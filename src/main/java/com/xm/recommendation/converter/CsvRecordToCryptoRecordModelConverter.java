package com.xm.recommendation.converter;

import com.xm.recommendation.enums.CryptoCurrency;
import com.xm.recommendation.exception.FileProcessingException;
import com.xm.recommendation.model.CryptoRateModel;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Component
public class CsvRecordToCryptoRecordModelConverter implements Converter<CSVRecord, CryptoRateModel> {

    @Override
    public CryptoRateModel convert(CSVRecord source) {
        try {
            CryptoRateModel.CryptoRecordModelBuilder builder = CryptoRateModel.builder();

            String timestamp = source.get("timestamp");
            String symbol = source.get("symbol");
            String price = source.get("price");

            builder.timeStamp(new Timestamp(Long.parseLong(timestamp)));
            builder.cryptoCurrency(CryptoCurrency.valueOf(symbol));
            builder.price(new BigDecimal(price));

            return builder.build();
        } catch (IllegalArgumentException e) {
            throw new FileProcessingException(e.getMessage());
        }
    }
}
