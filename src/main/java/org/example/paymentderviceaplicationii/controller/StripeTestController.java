package org.example.paymentderviceaplicationii.controller;

import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.example.paymentderviceaplicationii.model.dto.StripeRequestDTO;
import org.example.paymentderviceaplicationii.model.dto.StripeResponseDTO;
import org.example.paymentderviceaplicationii.service.StripeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stripe-test")
@RequiredArgsConstructor
public class StripeTestController {
    private final StripeService stripeService;

    @PostMapping
    public ResponseEntity<StripeResponseDTO> createPaymentIntent(@RequestBody StripeRequestDTO request) throws StripeException {
        StripeResponseDTO response = stripeService.createStripePayment(request);

        return ResponseEntity.ok(response);
    }
}
