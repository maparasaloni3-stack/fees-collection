package com.example.fees_collection.controller;


import com.example.fees_collection.dto.APIResponse;
import com.example.fees_collection.dto.FessPaymentRequest;
import com.example.fees_collection.model.Receipt;
import com.example.fees_collection.service.ReceiptHtmlService;
import com.example.fees_collection.service.ReceiptService;
import com.example.fees_collection.service.StudentClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    @Autowired
    private ReceiptService service;

    @Autowired
    ReceiptHtmlService receiptHtmlService;

    @PostMapping("/collectFee")
    public APIResponse collectFee(@RequestBody FessPaymentRequest feesPaymentReq) {

        if(Objects.nonNull(service.validateAndSaveReceipt(feesPaymentReq))){
            return new APIResponse("Payment Successfull","success", HttpStatus.OK);
        }
        else return new APIResponse("Payment is not Successfull","error", HttpStatus.BAD_REQUEST);


    }



    @GetMapping("/{studentId}/download")
    public ResponseEntity<byte[]> downloadReceipt(@PathVariable Long studentId) throws IOException {

        Receipt receipt = service.validateStudentAndGenerateReceipt(studentId);

        if (receipt == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else {

            String html = receiptHtmlService.buildHtml(receipt);
            byte[] pdfBytes = service.generateReceiptPdf(html);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=receipt_" + studentId + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        }
    }
}