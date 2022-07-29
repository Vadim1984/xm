package com.xm.recommendation.repository;

import com.xm.recommendation.model.CryptoRateModel;
import org.springframework.data.repository.CrudRepository;

public interface CryptoRepository extends CrudRepository<CryptoRateModel, Long> {
}
