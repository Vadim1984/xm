package com.xm.recommendation.service;

import com.xm.recommendation.model.CryptoRecordModel;

import java.util.List;

public interface CryptoRatesReaderService {

    /**
     * Read all {@link com.xm.recommendation.enums.CryptoCurrency supported crypto currencies}
     *
     * @return list of {@link com.xm.recommendation.model.CryptoRecordModel CryptoRecordModel}
     */
    List<CryptoRecordModel> read();

    /**
     * Read {@link com.xm.recommendation.enums.CryptoCurrency supported crypto currencies}
     *
     * @param fileName - file to read
     * @return list of {@link com.xm.recommendation.model.CryptoRecordModel CryptoRecordModel}
     */
    List<CryptoRecordModel> read(String fileName);
}
