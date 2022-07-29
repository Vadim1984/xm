package com.xm.recommendation.service;

import com.xm.recommendation.model.CryptoCurrencyModel;
import com.xm.recommendation.model.CryptoRateModel;

import java.util.List;
import java.util.Optional;

public interface CryptoService {

    /**
     * Read crypto-rates from file
     *
     * @param fileName file to read
     * @return list of {@link CryptoRateModel CryptoRecordModel}
     */
    List<CryptoRateModel> read(String fileName);


    /**
     * import all crypto-rates into DB for all supported currencies
     */
    void importRatesIntoDb();

    /**
     * Get all crypto-rates
     *
     * @return Iterable of {@link CryptoRateModel CryptoRateModel}
     */
    Iterable<CryptoRateModel> getAllCurrencyRates();

    /**
     * Get CryptoCurrencyModel
     *
     * @param currencyCode input currencyCode
     * @return Optional of {@link CryptoCurrencyModel CryptoCurrencyModel}
     */
    Optional<CryptoCurrencyModel> getCryptoCurrencyByCurrencyCode(String currencyCode);
}
