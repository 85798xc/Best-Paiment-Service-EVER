package org.example.paymentderviceaplicationii.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.paymentderviceaplicationii.model.dto.PaymentTransactionRequestDTO;
import org.example.paymentderviceaplicationii.service.PaymentTransactionServiceImpl;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
@Tag(name = "Payment Transactions", description = "Endpoints for managing payment transactions")
public class PaymentTransactionController {

    private final PaymentTransactionServiceImpl paymentTransactionServiceImpl;

    @Operation(summary = "Initiate a payment transaction")
    @PostMapping("/process")
    public ResponseEntity<EntityModel<String>> processPayment(@RequestBody PaymentTransactionRequestDTO request) {
        String paymentUrl = paymentTransactionServiceImpl.createPaymentTransaction(request);

        EntityModel<String> resource = EntityModel.of(paymentUrl);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PaymentTransactionController.class)
                .paymentSuccess("sampleSessionId")).withRel("success"));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PaymentTransactionController.class)
                .paymentCancel()).withRel("cancel"));

        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Handle successful payment redirect")
    @GetMapping("/success")
    public ResponseEntity<EntityModel<String>> paymentSuccess(@RequestParam("session_id") String sessionId) {
        String message = "Payment successful! Session ID: " + sessionId;

        EntityModel<String> resource = EntityModel.of(message);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PaymentTransactionController.class)
                .processPayment(null)).withRel("retry"));

        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Handle payment cancellation")
    @GetMapping("/cancel")
    public ResponseEntity<EntityModel<String>> paymentCancel() {
        String message = "Payment cancelled.";

        EntityModel<String> resource = EntityModel.of(message);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PaymentTransactionController.class)
                .processPayment(null)).withRel("retry"));

        return ResponseEntity.ok(resource);
    }
}