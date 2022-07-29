package com.xm.recommendation.service;

import com.xm.recommendation.model.CryptoRateModel;

import java.util.List;
import java.util.Optional;

public interface DataImportService {
    /**
     * Read crypto-rates from file
     *
     * @param fileName file to read
     * @return Optional list of {@link CryptoRateModel CryptoRecordModel}
     */
    Optional<List<CryptoRateModel>> read(String fileName);


    /**
     * import all crypto-rates into DB for all supported currencies
     */
    void importRatesIntoDb();
}
