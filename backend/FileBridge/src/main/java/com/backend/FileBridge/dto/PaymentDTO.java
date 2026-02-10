package com.backend.FileBridge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Boolean success;
    private String message;

    private String paymentIntentId;
    private String clientSecret;

    private Integer amount;
    private String currency;
    private String planId;

    private Integer credits;
}

