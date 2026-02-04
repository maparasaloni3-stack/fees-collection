package com.example.fees_collection.exceptions;


import com.example.fees_collection.dto.APIResponse;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FeesAlreadyPaidException.class)
    public ResponseEntity<APIResponse> handleFeesAlreadyPaid(FeesAlreadyPaidException ex) {
        // Log the error
        log.error("FeesAlreadyPaidException: {}", ex.getMessage());

        // Build response with proper HTTP status
        APIResponse response = new APIResponse(ex.getMessage(), "error", HttpStatus.CONFLICT);

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(io.github.resilience4j.circuitbreaker.CallNotPermittedException.class)
    public ResponseEntity<Map<String,Object>> handleCircuitBreakerOpen(CallNotPermittedException ex) {
        log.error("Error: {}", ex.getMessage());
        Map<String,Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 503);
        body.put("error", "Service Unavailable");
        body.put("message", "Remote service is currently unavailable. Please try again later.");
        return new ResponseEntity<>(body, HttpStatus.SERVICE_UNAVAILABLE);
    }




}
