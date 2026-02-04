package com.example.fees_collection.repository;


import com.example.fees_collection.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    Receipt findByStudentId(Long studentId);
}