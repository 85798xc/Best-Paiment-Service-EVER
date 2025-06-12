package org.example.paymentderviceaplicationii.controller;

import lombok.RequiredArgsConstructor;
import org.example.paymentderviceaplicationii.model.dto.PaymentTransactionRequestDTO;
import org.example.paymentderviceaplicationii.service.PaymentTransactionServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentTransactionController {
    private final PaymentTransactionServiceImpl paymentTransactionServiceImpl;

    @PostMapping("/process")
    public ResponseEntity<String> processPayment(@RequestBody PaymentTransactionRequestDTO request) {
        String paymentUrl = paymentTransactionServiceImpl.createPaymentTransaction(request);
        return ResponseEntity.ok(paymentUrl);
    }

    @GetMapping("/success")
    public ResponseEntity<String> paymentSuccess(@RequestParam("session_id") String sessionId) {
        return ResponseEntity.ok("Payment successful! Session ID: " + sessionId);
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> paymentCancel() {
        return ResponseEntity.ok("Payment cancelled.");
    }
}
