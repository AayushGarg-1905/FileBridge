package com.backend.FileBridge.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "payment_transactions")
public class PaymentTransactionDocument {
    private String id;
    private String clerkId;
    private String orderId;
    private String paymentId;
    private Integer amount;
    private String currency;
    private Integer creditsAdded;
    private String status;
    private String planId;
    private LocalDateTime transactionDate;
    private String userEmail;
    private String userName;
}
