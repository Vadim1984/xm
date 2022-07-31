package com.xm.recommendation.converter;

import com.xm.recommendation.exception.CsvRecordParsingException;
import com.xm.recommendation.model.CryptoCurrencyModel;
import com.xm.recommendation.repository.CryptoCurrencyRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class CsvRecordToCryptoRecordModelConverterTest {
    private static final String TIMESTAMP_FILED = "timestamp";
    private static final String SYMBOL_FIELD = "symbol";
    private static final String PRICE_FIELD = "price";

    @InjectMocks
    private CsvRecordToCryptoRecordModelConverter testInstance;
    @Mock
    private CryptoCurrencyRepository cryptoCurrencyRepository;
    @Mock
    private CryptoCurrencyModel cryptoCurrencyModel;

    @Test
    void testConvertWillThrowExceptionWhenSourceIsNull() {
        assertThatThrownBy(() -> testInstance.convert(null))
                .isInstanceOf(CsvRecordParsingException.class)
                .hasMessage("Csv file record cannot be null");
    }

    @Test
    void testConvertWillThrowExceptionWhenSourceHasInvalidTimeStamp() throws IOException {
        String invalidTimeStamp = "abc";
        String cryptoCurrencyCode = "BTC";
        String price = "12.12";
        CSVParser csvParser = CSVFormat.DEFAULT.builder()
                .setHeader(TIMESTAMP_FILED, SYMBOL_FIELD, PRICE_FIELD)
                .build()
                .parse(new StringReader(invalidTimeStamp + "," + cryptoCurrencyCode + "," + price));
        CSVRecord record = csvParser.getRecords().get(0);

        assertThatThrownBy(() -> testInstance.convert(record))
                .isInstanceOf(CsvRecordParsingException.class)
                .hasMessage("For input string: \"abc\"");
    }

    @Test
    void testConvertWillThrowExceptionWhenSourceHasInvalidPrice() throws IOException {
        String timeStamp = "1641009600000";
        String cryptoCurrencyCode = "BTC";
        String invalidPrice = "abc";
        CSVParser csvParser = CSVFormat.DEFAULT.builder()
                .setHeader(TIMESTAMP_FILED, SYMBOL_FIELD, PRICE_FIELD)
                .build()
                .parse(new StringReader(timeStamp + "," + cryptoCurrencyCode + "," + invalidPrice));
        CSVRecord record = csvParser.getRecords().get(0);

        assertThatThrownBy(() -> testInstance.convert(record))
                .isInstanceOf(CsvRecordParsingException.class)
                .hasMessage("Character a is neither a decimal digit number, decimal point, nor \"e\" notation exponential mark.");
    }

}
