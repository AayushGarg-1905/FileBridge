package com.backend.FileBridge.controller;

import com.backend.FileBridge.dto.PaymentDTO;
import com.backend.FileBridge.dto.PaymentVerificationDTO;
import com.backend.FileBridge.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class paymentController {

    private final PaymentService paymentService;

    @PostMapping("/create-order")
    public ResponseEntity<?>createOrder(@RequestBody PaymentDTO paymentDTO){
        PaymentDTO resp = paymentService.createOrder(paymentDTO);
        if(resp.getSuccess()){
            return ResponseEntity.ok(resp);
        }
        return ResponseEntity.badRequest().body(resp);
    }

    @PostMapping("/verify-payment")
    public ResponseEntity<?>verifyPayment(@RequestBody PaymentVerificationDTO paymentVerificationDTO){
        PaymentDTO paymentDTO = paymentService.verifyPayment(paymentVerificationDTO);
        if(paymentDTO.getSuccess()){
            return ResponseEntity.ok(paymentDTO);
        }
        return ResponseEntity.badRequest().body(paymentDTO);
    }
}
