package com.example.fees_collection.service;


import com.example.fees_collection.dto.FessPaymentRequest;
import com.example.fees_collection.exceptions.FeesAlreadyPaidException;
import com.example.fees_collection.model.Receipt;
import com.example.fees_collection.repository.ReceiptRepository;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;import java.io.IOException;import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class ReceiptService {

    @Autowired
    private ReceiptRepository repo;

    @Autowired
    private StudentClient studentClient;



    public Receipt validateAndSaveReceipt(FessPaymentRequest feesPaymentReq) {

        Receipt receipt = new Receipt();
        Receipt saved = new  Receipt();
        Map student = studentClient.getStudentByStudentId(feesPaymentReq.getStudentId());

        log.info("Student Id : " + (Objects.nonNull(student)?student.get("studentId").toString() :"no student found"));
        if(student != null) {
            Receipt receiptExists = repo.findByStudentId(feesPaymentReq.getStudentId());
            if (receiptExists != null) {
                throw new FeesAlreadyPaidException("Fees for student " + feesPaymentReq.getStudentId() + " have already been paid.");
            }else {
                receipt.setStudentName((String) student.get("studentName"));
                receipt.setStudentId(feesPaymentReq.getStudentId());
                receipt.setSchool((String) student.get("school"));
                receipt.setParentName(feesPaymentReq.getParentName());
                receipt.setEmail(feesPaymentReq.getEmail());
                receipt.setReceiptNumber("RCPT-" + UUID.randomUUID());
                receipt.setAmount(feesPaymentReq.getAmount());
                receipt.setPaymentMode(feesPaymentReq.getPaymentMode());
                receipt.setPaymentDate(LocalDate.now());
                receipt.setCardNumber(feesPaymentReq.getCardNumber());
                saved = repo.save(receipt);
            }
        }
    return saved;

    }

    public byte[] generateReceiptPdf(String html) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.withHtmlContent(html, null);
        builder.toStream(out);
        builder.run();

        return out.toByteArray();

    }

    public Receipt validateStudentAndGenerateReceipt(Long studentId) {
        Map student = studentClient.getStudentByStudentId(studentId);

        Receipt receipt = repo.findByStudentId(studentId);
        return receipt;
    }
}