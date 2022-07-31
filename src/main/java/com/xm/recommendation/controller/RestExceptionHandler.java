package com.xm.recommendation.controller;

import com.xm.recommendation.exception.CalculationNormalizedRangeException;
import com.xm.recommendation.exception.CryptoRecordNotFoundException;
import com.xm.recommendation.exception.CurrencyNotSupportedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {
            CalculationNormalizedRangeException.class,
            CurrencyNotSupportedException.class})
    public ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());

        return ResponseEntity.badRequest()
                .body(body);
    }

    @ExceptionHandler(value = {CryptoRecordNotFoundException.class})
    public ResponseEntity<Object> handleNoContent(Exception ex, WebRequest request) {
        return ResponseEntity.noContent()
                .header("Content-Length", "0")
                .build();
    }
}
