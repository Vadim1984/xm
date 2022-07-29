package com.xm.recommendation.repository;

import com.xm.recommendation.model.CryptoRateModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface CryptoRateRepository extends CrudRepository<CryptoRateModel, Long> {

    @Query(" SELECT MIN(cr.price)  \n" +
            "  FROM crypto_rate AS cr  \n" +
            "       JOIN cr.cryptoCurrency AS cc \n" +
            " WHERE cc.currencyCode = :currencyCode")
    Optional<BigDecimal> getRateRecordWithMinRateForCurrencyCode(String currencyCode);

    @Query(" SELECT MAX(cr.price)  \n" +
            "  FROM crypto_rate AS cr  \n" +
            "       JOIN cr.cryptoCurrency AS cc \n" +
            " WHERE cc.currencyCode = :currencyCode")
    Optional<BigDecimal> getRateRecordWithMaxRateForCurrencyCode(String currencyCode);
}
