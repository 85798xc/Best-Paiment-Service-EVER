package org.example.paymentderviceaplicationii.controller;

import lombok.RequiredArgsConstructor;
import org.example.paymentderviceaplicationii.model.dto.PayPalRequestDTO;
import org.example.paymentderviceaplicationii.model.dto.PayPalResponseDTO;
import org.example.paymentderviceaplicationii.service.PayPalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paypal-test")
@RequiredArgsConstructor
public class PayPalTestController {

    private final PayPalService payPalService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody PayPalRequestDTO request) {
        String response = payPalService.createPayPalOrder(request);

        return ResponseEntity.ok(response);
    }

}
