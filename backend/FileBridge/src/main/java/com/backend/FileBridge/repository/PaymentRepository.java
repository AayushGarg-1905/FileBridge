package com.backend.FileBridge.repository;

import com.backend.FileBridge.document.PaymentTransactionDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PaymentRepository extends MongoRepository<PaymentTransactionDocument,String> {

    List<PaymentTransactionDocument>findByClerkId(String clerkId);
    List<PaymentTransactionDocument>findByClerkIdOrderByTransactionDateDesc(String clerkId);
    List<PaymentTransactionDocument>findByClerkIdAndStatusOrderByTransactionDateDesc(String clerkId, String status);

}
