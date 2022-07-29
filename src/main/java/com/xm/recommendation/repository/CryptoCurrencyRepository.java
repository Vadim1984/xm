package com.xm.recommendation.repository;

import com.xm.recommendation.model.CryptoCurrencyModel;
import org.springframework.data.repository.CrudRepository;

public interface CryptoCurrencyRepository extends CrudRepository<CryptoCurrencyModel, Long> {
    CryptoCurrencyModel findByCurrencyCode(String currencyCode);
}
