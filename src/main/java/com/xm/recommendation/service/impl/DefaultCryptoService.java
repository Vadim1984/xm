package com.xm.recommendation.service.impl;

import com.xm.recommendation.converter.CsvRecordToCryptoRecordModelConverter;
import com.xm.recommendation.exception.FileProcessingException;
import com.xm.recommendation.model.CryptoCurrencyModel;
import com.xm.recommendation.model.CryptoRateModel;
import com.xm.recommendation.repository.CryptoCurrencyRepository;
import com.xm.recommendation.repository.CryptoRateRepository;
import com.xm.recommendation.service.CryptoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultCryptoService implements CryptoService {
    @Value("${csv.file.name.format}")
    private String fileNameFormat;

    private final CsvRecordToCryptoRecordModelConverter converter;
    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    private final CryptoRateRepository cryptoRateRepository;

    @Override
    public List<CryptoRateModel> read(String fileName) {
        log.debug("Read from csv file {} ", fileName);
        try {
            ClassPathResource resource = new ClassPathResource("crypto_rates/" + fileName);
            Reader in = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            CSVParser records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);

            return StreamSupport.stream(records.spliterator(), false)
                    .map(converter::convert)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("error reading file {}", fileName, e);
            throw new FileProcessingException(e.getMessage());
        }
    }

    @Override
    public void importRatesIntoDb() {
        List<CryptoRateModel> cryptoRecords = StreamSupport.stream(cryptoCurrencyRepository.findAll().spliterator(), false)
                .map(CryptoCurrencyModel::getCurrencyCode)
                .map(supportedCryptoCurrency -> String.format(fileNameFormat, supportedCryptoCurrency))
                .flatMap(fileName -> read(fileName).stream())
                .collect(Collectors.toList());

        log.debug("Read from csv files {} records", cryptoRecords.size());
        Iterable<CryptoRateModel> savedCryptoRecord = cryptoRateRepository.saveAll(cryptoRecords);
        log.debug("Insert into DB {} records", StreamSupport.stream(savedCryptoRecord.spliterator(), false).count());
    }

    @Override
    public Iterable<CryptoRateModel> getAllCurrencyRates() {
        return cryptoRateRepository.findAll();
    }

    @Override
    public Optional<CryptoCurrencyModel> getCryptoCurrencyByCurrencyCode(String currencyCode) {
        return Optional.ofNullable(cryptoCurrencyRepository.findByCurrencyCode(currencyCode));
    }

}
