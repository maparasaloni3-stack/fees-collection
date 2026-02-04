package com.example.fees_collection.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Map;
@Slf4j
@Service
public class StudentClient {
    private final RestTemplate restTemplate = new RestTemplate();
    // Change host/port if student-service runs elsewhere!

    @Value("${student_service_url}")
    private String student_service_url;



    @CircuitBreaker(name = "feeServiceRestCall", fallbackMethod = "fallbackPaymentStatus")
    public Map getStudentByStudentId(Long studentId) {
        String url = student_service_url + "/students/" + studentId;
        log.info("Student Service URL: " + url);
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        return response.getBody();
    }

    public String fallbackPaymentStatus(Long studentId, Throwable throwable) {
        return "Payment service unavailable. Try again later.";
    }
}