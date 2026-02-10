package com.backend.FileBridge.service;

import com.backend.FileBridge.document.PaymentTransactionDocument;
import com.backend.FileBridge.document.ProfileDocument;
import com.backend.FileBridge.dto.PaymentDTO;
import com.backend.FileBridge.dto.PaymentVerificationDTO;
import com.backend.FileBridge.repository.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Formatter;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final ProfileService profileService;
    private final UserCreditsService userCreditsService;
    private final PaymentRepository paymentRepository;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    public PaymentDTO createOrder(PaymentDTO paymentDTO) {
        try {
            ProfileDocument profile = profileService.getCurrentProfile();

            PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .setAmount((long) paymentDTO.getAmount())
                            .setCurrency(paymentDTO.getCurrency())
                            .putMetadata("clerkId", profile.getClerkId())
                            .putMetadata("planId", paymentDTO.getPlanId())
                            .build();

            PaymentIntent intent = PaymentIntent.create(params);

            PaymentTransactionDocument trxn =
                    PaymentTransactionDocument.builder()
                            .clerkId(profile.getClerkId())
                            .orderId(intent.getId())
                            .amount(paymentDTO.getAmount())
                            .currency(paymentDTO.getCurrency())
                            .status("PENDING")
                            .planId(paymentDTO.getPlanId())
                            .transactionDate(LocalDateTime.now())
                            .userEmail(profile.getEmail())
                            .userName(profile.getFirstName() + " " + profile.getLastName())
                            .build();

            paymentRepository.save(trxn);

            return PaymentDTO.builder()
                    .success(true)
                    .paymentIntentId(intent.getId())
                    .clientSecret(intent.getClientSecret())
                    .message("Payment intent created")
                    .build();

        } catch (Exception e) {
            return PaymentDTO.builder()
                    .success(false)
                    .message("Stripe error: " + e.getMessage())
                    .build();
        }
    }

    public PaymentDTO verifyPayment(PaymentVerificationDTO dto) {
        try {
            ProfileDocument profile = profileService.getCurrentProfile();

            PaymentIntent intent = PaymentIntent.retrieve(dto.getPaymentIntentId());

            if (!"succeeded".equals(intent.getStatus())) {
                updateTransactionStatus(intent.getId(), "FAILED", null, null);
                return PaymentDTO.builder()
                        .success(false)
                        .message("Payment not successful")
                        .build();
            }

            int creditsToAdd = 0;
            String plan = "BASIC";

            switch (dto.getPlanId()) {
                case "premium":
                    creditsToAdd = 500;
                    plan = "PREMIUM";
                    break;
                case "ultimate":
                    creditsToAdd = 5000;
                    plan = "ULTIMATE";
                    break;
            }

            userCreditsService.addCredits(profile.getClerkId(), creditsToAdd, plan);

            updateTransactionStatus(
                    intent.getId(),
                    "SUCCESS",
                    intent.getLatestCharge(),
                    creditsToAdd
            );

            return PaymentDTO.builder()
                    .success(true)
                    .message("Credits added successfully")
                    .credits(userCreditsService.getUserCredits(profile.getClerkId()).getCredits())
                    .build();

        } catch (Exception e) {
            return PaymentDTO.builder()
                    .success(false)
                    .message("Verification error: " + e.getMessage())
                    .build();
        }
    }

    private void updateTransactionStatus(
            String orderId,
            String status,
            String paymentId,
            Integer creditsAdded
    ) {
        paymentRepository.findByClerkId(profileService.getCurrentProfile().getClerkId())
                .stream()
                .filter(t -> orderId.equals(t.getOrderId()))
                .findFirst()
                .ifPresent(t -> {
                    t.setStatus(status);
                    t.setPaymentId(paymentId);
                    t.setCreditsAdded(creditsAdded);
                    paymentRepository.save(t);
                });
    }
}

