package com.xm.recommendation.controller;

import com.xm.recommendation.model.CryptoRateModel;
import com.xm.recommendation.service.CryptoRatesReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cryptos")
public class CryptoController {

    private final CryptoRatesReaderService cryptoRatesReaderService;

    @GetMapping("/{fileName}")
    public List<CryptoRateModel> getCryptos(@PathVariable String fileName){
        return cryptoRatesReaderService.read(fileName);
    }

    @GetMapping
    public List<CryptoRateModel> getAllCryptos(){
        return cryptoRatesReaderService.read();
    }
}
