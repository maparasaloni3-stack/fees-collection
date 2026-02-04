package com.example.fees_collection.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String receiptNumber;
    private Long studentId;
    private String studentName;
    private String parentName;
    private String school;
    private Double amount;
    private LocalDate paymentDate;
    private String paymentMode;
    private String cardNumber;
    private String email; // for notification
}