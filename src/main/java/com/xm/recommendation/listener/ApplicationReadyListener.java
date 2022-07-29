package com.xm.recommendation.listener;

import com.xm.recommendation.service.CryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {
    private final CryptoService cryptoRatesService;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        cryptoRatesService.importRatesIntoDb();
    }
}
