package com.backend.FileBridge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
public class PaymentVerificationDTO {
    private String paymentIntentId;
    private String planId;
}
