package com.example.fees_collection.dto;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FessPaymentRequest {

    @NonNull
    private Double amount;

    @NonNull
    private LocalDate paymentDate;

    @NonNull
    private String paymentMode;

    @NonNull
    private String email;

    @NonNull
    private Long studentId;

    @NonNull
    private String parentName;

    @NonNull
    private String cardNumber;
}
