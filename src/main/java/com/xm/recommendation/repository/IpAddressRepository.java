package com.xm.recommendation.repository;

import com.xm.recommendation.model.IpAddressModel;
import org.springframework.data.repository.CrudRepository;

public interface IpAddressRepository extends CrudRepository<IpAddressModel, Long> {
    IpAddressModel findByIpAddress(String ipAddress);
}
