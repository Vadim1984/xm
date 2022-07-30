package com.xm.recommendation.repository;

import com.xm.recommendation.model.CryptoRateModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CryptoRateRepository extends CrudRepository<CryptoRateModel, Long> {

    @Query(value=" " +
            " SELECT cr.* \n" +
            "   FROM crypto_rate AS cr \n" +
            "  WHERE cr.price = ( SELECT MIN(cr.price)  \n" +
            "                       FROM crypto_rate AS cr  \n" +
            "                            JOIN crypto_currency AS cc ON cr.crypto_currency_id = cc.id \n" +
            "                      WHERE cc.currency_code = :currencyCode" +
            "                        AND cr.time_stamp between :startDate and :endDate ) \n" +
            "     AND cr.time_stamp between :startDate and :endDate \n" +
            "  LIMIT 1", nativeQuery = true)
    Optional<CryptoRateModel> findRateRecordWithMinRateByCurrencyCode(String currencyCode, LocalDateTime startDate, LocalDateTime endDate);

    @Query(value=" " +
            " SELECT cr.* \n" +
            "   FROM crypto_rate AS cr \n" +
            "  WHERE cr.price = ( SELECT MAX(cr.price)  \n" +
            "                       FROM crypto_rate AS cr  \n" +
            "                            JOIN crypto_currency AS cc ON cr.crypto_currency_id = cc.id \n" +
            "                      WHERE cc.currency_code = :currencyCode " +
            "                        AND cr.time_stamp between :startDate and :endDate ) \n" +
            "    AND cr.time_stamp between :startDate and :endDate \n" +
            "  LIMIT 1", nativeQuery = true)
    Optional<CryptoRateModel> findRateRecordWithMaxRateByCurrencyCode(String currencyCode, LocalDateTime startDate, LocalDateTime endDate);

    @Query(value="  " +
            " SELECT cr.* \n" +
            "   FROM crypto_rate AS cr \n" +
            "  WHERE cr.time_stamp = ( SELECT MIN(cr.time_stamp) \n" +
            "                           FROM crypto_rate AS cr \n" +
            "                                JOIN crypto_currency AS cc ON cr.crypto_currency_id = cc.id \n" +
            "                          WHERE cc.currency_code = :currencyCode " +
            "                            AND cr.time_stamp between :startDate and :endDate ) \n" +
            "    AND cr.time_stamp between :startDate and :endDate \n" +
            "  LIMIT 1", nativeQuery = true)
    Optional<CryptoRateModel> findOldestRateRecordByCurrencyCode(String currencyCode, LocalDateTime startDate, LocalDateTime endDate);

    @Query(value="  " +
            " SELECT cr.* \n" +
            "   FROM crypto_rate AS cr \n" +
            "  WHERE cr.time_stamp = ( SELECT MAX(cr.time_stamp) \n" +
            "                           FROM crypto_rate AS cr \n" +
            "                                JOIN crypto_currency AS cc ON cr.crypto_currency_id = cc.id \n" +
            "                          WHERE cc.currency_code = :currencyCode " +
            "                            AND cr.time_stamp between :startDate and :endDate ) \n" +
            "    AND cr.time_stamp between :startDate and :endDate \n" +
            "  LIMIT 1", nativeQuery = true)
    Optional<CryptoRateModel> findNewestRateRecordByCurrencyCode(String currencyCode, LocalDateTime startDate, LocalDateTime endDate);
}
