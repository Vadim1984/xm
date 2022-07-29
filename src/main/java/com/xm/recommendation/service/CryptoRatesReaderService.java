package com.xm.recommendation.service;

import com.xm.recommendation.model.CryptoRateModel;

import java.util.List;

public interface CryptoRatesReaderService {

    /**
     * Read all {@link com.xm.recommendation.enums.CryptoCurrency supported crypto currencies}
     *
     * @return list of {@link CryptoRateModel CryptoRecordModel}
     */
    List<CryptoRateModel> read();

    /**
     * Read {@link com.xm.recommendation.enums.CryptoCurrency supported crypto currencies}
     *
     * @param fileName - file to read
     * @return list of {@link CryptoRateModel CryptoRecordModel}
     */
    List<CryptoRateModel> read(String fileName);
}
