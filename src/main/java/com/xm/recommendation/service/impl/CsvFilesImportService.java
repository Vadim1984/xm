package com.xm.recommendation.service.impl;

import com.xm.recommendation.converter.CsvRecordToCryptoRecordModelConverter;
import com.xm.recommendation.model.CryptoCurrencyModel;
import com.xm.recommendation.model.CryptoRateModel;
import com.xm.recommendation.repository.CryptoCurrencyRepository;
import com.xm.recommendation.repository.CryptoRateRepository;
import com.xm.recommendation.service.DataImportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@RequiredArgsConstructor
@Service
public class CsvFilesImportService implements DataImportService {
    @Value("${csv.file.name.format}")
    private String fileNameFormat;
    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    private final CryptoRateRepository cryptoRateRepository;
    private final CsvRecordToCryptoRecordModelConverter converter;

    @Override
    public Optional<List<CryptoRateModel>> read(String fileName) {
        log.debug("Read from csv file {} ", fileName);
        try {
            ClassPathResource resource = new ClassPathResource("crypto_rates/" + fileName);
            Reader in = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            CSVFormat csvFormat = CSVFormat.RFC4180.builder()
                    .setHeader("timestamp", "symbol", "price")
                    .setSkipHeaderRecord(true)
                    .build();
            CSVParser parser = csvFormat.parse(in);

            List<CryptoRateModel> cryptoRates = Optional.ofNullable(parser.getRecords())
                    .orElseGet(Collections::emptyList).stream()
                    .map(this::readLine)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());

            return Optional.of(cryptoRates);
        } catch (IOException e) {
            log.error("error reading file {}", fileName, e);
            return Optional.empty();
        }
    }

    @Override
    public void importRatesIntoDb() {
        List<CryptoRateModel> cryptoRecords = StreamSupport.stream(cryptoCurrencyRepository.findAll().spliterator(), false)
                .map(CryptoCurrencyModel::getCurrencyCode)
                .map(supportedCryptoCurrency -> String.format(fileNameFormat, supportedCryptoCurrency))
                .flatMap(fileName -> read(fileName).orElseGet(Collections::emptyList).stream())
                .collect(Collectors.toList());

        log.debug("Read from csv files {} records", cryptoRecords.size());
        Iterable<CryptoRateModel> savedCryptoRecord = cryptoRateRepository.saveAll(cryptoRecords);
        log.debug("Insert into DB {} records", StreamSupport.stream(savedCryptoRecord.spliterator(), false).count());
    }

    private Optional<CryptoRateModel> readLine(CSVRecord csvRecord) {
        try {
            return Optional.ofNullable(converter.convert(csvRecord));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
