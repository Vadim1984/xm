package com.xm.recommendation.service.impl;

import com.xm.recommendation.converter.CsvRecordToCryptoRecordModelConverter;
import com.xm.recommendation.enums.CryptoCurrency;
import com.xm.recommendation.exception.FileProcessingException;
import com.xm.recommendation.model.CryptoRecordModel;
import com.xm.recommendation.service.CryptoRatesReaderService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class CsvCryptoRatesReader implements CryptoRatesReaderService {
    @Value("${csv.file.name.format}")
    private String fileNameFormat;

    private final CsvRecordToCryptoRecordModelConverter converter;

    @Override
    public List<CryptoRecordModel> read() {
        return Stream.of(CryptoCurrency.values())
                .map(CryptoCurrency::toString)
                .map(supportedCryptoCurrency -> String.format(fileNameFormat, supportedCryptoCurrency))
                .flatMap(fileName -> read(fileName).stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<CryptoRecordModel> read(String fileName) {
        try {
            ClassPathResource resource = new ClassPathResource("crypto_rates/" + fileName);
            Reader in = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            CSVParser records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);

            return StreamSupport.stream(records.spliterator(), false)
                    .map(converter::convert)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new FileProcessingException(e.getMessage());
        }
    }
}
