package com.xm.recommendation.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CryptoStatisticsDto {
    private CryptoRateDto oldestRecord;
    private CryptoRateDto newestRecord;
    private CryptoRateDto recordWithMaxRate;
    private CryptoRateDto recordWithMinRate;
}
