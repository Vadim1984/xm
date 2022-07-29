package com.xm.recommendation.listener;

import com.xm.recommendation.model.CryptoRateModel;
import com.xm.recommendation.repository.CryptoRepository;
import com.xm.recommendation.service.CryptoRatesReaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.StreamSupport;

@Slf4j
@RequiredArgsConstructor
@Component
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {
    private final CryptoRatesReaderService cryptoRatesReaderService;
    private final CryptoRepository cryptoRepository;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        List<CryptoRateModel> cryptoRecords = cryptoRatesReaderService.read();
        log.debug("Read from csv files {} records", cryptoRecords.size());
        Iterable<CryptoRateModel> savedCryptoRecord = cryptoRepository.saveAll(cryptoRecords);
        log.debug("Insert into DB {} records", StreamSupport.stream(savedCryptoRecord.spliterator(), false).count());
    }
}
