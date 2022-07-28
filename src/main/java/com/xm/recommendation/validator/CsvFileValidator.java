package com.xm.recommendation.validator;

import com.xm.recommendation.enums.CryptoCurrency;
import com.xm.recommendation.exception.FileProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class CsvFileValidator {

    @Value("${csv.file.name.format}")
    private String fileNameFormat;

    void validateFileName(String fileName) {
        boolean isFileForSupportedCurrency = Stream.of(CryptoCurrency.values())
                .map(CryptoCurrency::toString)
                .map(supportedCryptoCurrency -> String.format(fileNameFormat, supportedCryptoCurrency))
                .anyMatch(expectedFileName -> expectedFileName.equals(fileName));

        if (!isFileForSupportedCurrency) {
            throw new FileProcessingException(String.format("file name [%s] is not supported", fileName));
        }
    }


}
