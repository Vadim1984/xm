package com.xm.recommendation.repository;

import com.xm.recommendation.model.CryptoRateModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CryptoRateRepository extends CrudRepository<CryptoRateModel, Long> {

    @Query(value=" " +
            " SELECT cr.* \n" +
            "   FROM crypto_rate AS cr \n" +
            "  WHERE cr.price = ( SELECT MIN(cr.price)  \n" +
            "                       FROM crypto_rate AS cr  \n" +
            "                            JOIN crypto_currency AS cc ON cr.crypto_currency_id = cc.id \n" +
            "                      WHERE cc.currency_code = :currencyCode ) \n" +
            "  LIMIT 1", nativeQuery = true)
    Optional<CryptoRateModel> getRateRecordWithMinRateForCurrencyCode(String currencyCode);

    @Query(value=" " +
            " SELECT cr.* \n" +
            "   FROM crypto_rate AS cr \n" +
            "  WHERE cr.price = ( SELECT MAX(cr.price)  \n" +
            "                       FROM crypto_rate AS cr  \n" +
            "                            JOIN crypto_currency AS cc ON cr.crypto_currency_id = cc.id \n" +
            "                      WHERE cc.currency_code = :currencyCode ) \n" +
            "  LIMIT 1", nativeQuery = true)
    Optional<CryptoRateModel> getRateRecordWithMaxRateForCurrencyCode(String currencyCode);

    @Query(value="  " +
            " SELECT cr.* \n" +
            "   FROM crypto_rate AS cr \n" +
            "  WHERE cr.time_stamp = ( SELECT MIN(cr.time_stamp) \n" +
            "                           FROM crypto_rate AS cr \n" +
            "                                JOIN crypto_currency AS cc ON cr.crypto_currency_id = cc.id \n" +
            "                          WHERE cc.currency_code = :currencyCode ) \n" +
            "  LIMIT 1", nativeQuery = true)
    Optional<CryptoRateModel> getOldestRateRecordForCurrencyCode(String currencyCode);

    @Query(value="  " +
            " SELECT cr.* \n" +
            "   FROM crypto_rate AS cr \n" +
            "  WHERE cr.time_stamp = ( SELECT MAX(cr.time_stamp) \n" +
            "                           FROM crypto_rate AS cr \n" +
            "                                JOIN crypto_currency AS cc ON cr.crypto_currency_id = cc.id \n" +
            "                          WHERE cc.currency_code = :currencyCode ) \n" +
            "  LIMIT 1", nativeQuery = true)
    Optional<CryptoRateModel> getNewestRateRecordForCurrencyCode(String currencyCode);
}
