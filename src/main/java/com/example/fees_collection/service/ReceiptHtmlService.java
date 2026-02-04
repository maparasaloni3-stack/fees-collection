package com.example.fees_collection.service;

import com.example.fees_collection.model.Receipt;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Service
public class ReceiptHtmlService {

    public String buildHtml(Receipt receipt) throws IOException {
        // Load template from resources
        ClassPathResource resource = new ClassPathResource("templates/receipt.html");
        String html = Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);

        // Replace placeholders with actual values
        html = html.replace("${studentName}", receipt.getStudentName())
                .replace("${studentId}", receipt.getStudentId().toString())
                .replace("${transactionDate}", receipt.getPaymentDate().toString())
                .replace("${cardLast4}", receipt.getCardNumber())
                .replace("${amount}", receipt.getAmount().toString())
                .replace("${parentName}", receipt.getParentName())
                .replace("${paymentMode}", receipt.getPaymentMode())
                .replace("${receiptNumber}", receipt.getReceiptNumber());

        return html;
    }
}
