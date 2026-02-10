package com.backend.FileBridge.controller;

import com.backend.FileBridge.document.PaymentTransactionDocument;
import com.backend.FileBridge.document.ProfileDocument;
import com.backend.FileBridge.repository.PaymentRepository;
import com.backend.FileBridge.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final PaymentRepository paymentRepository;
    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<?>getUserTransactions(){
        ProfileDocument profile = profileService.getCurrentProfile();
        String clerkId = profile.getClerkId();

        List<PaymentTransactionDocument>list = paymentRepository.findByClerkIdOrderByTransactionDateDesc(clerkId);
        return ResponseEntity.ok(list);
    }

}
