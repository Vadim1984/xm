package com.xm.recommendation.model;

import com.xm.recommendation.enums.CryptoCurrency;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@Entity(name = "crypto_record")
public class CryptoRecordModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    Timestamp timeStamp;
    @Enumerated(EnumType.STRING)
    CryptoCurrency cryptoCurrency;
    BigDecimal price;
}
