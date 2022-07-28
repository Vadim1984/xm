package com.xm.recommendation.repository;

import com.xm.recommendation.model.CryptoRecordModel;
import org.springframework.data.repository.CrudRepository;

public interface CryptoRepository extends CrudRepository<CryptoRecordModel, Long> {
}
