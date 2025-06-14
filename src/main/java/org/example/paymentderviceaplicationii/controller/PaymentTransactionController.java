package org.example.paymentderviceaplicationii.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Payment Transactions", description = "Endpoints for managing payment transactions")
public class PaymentTransactionController {

    private final PaymentTransactionServiceImpl paymentTransactionServiceImpl;

    @Operation(summary = "Initiate a payment transaction",
            description = "Creates a new payment transaction and returns the payment URL",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Payment URL generated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400",
                            description = "Invalid request parameters",
                            content = @Content),
                    @ApiResponse(responseCode = "500",
                            description = "Internal server error during payment processing",
                            content = @Content)
            })
    @PostMapping("/process")
    public ResponseEntity<String> processPayment(@RequestBody PaymentTransactionRequestDTO request) {
        String paymentUrl = paymentTransactionServiceImpl.createPaymentTransaction(request);
        return ResponseEntity.ok(paymentUrl);
    }

    @Operation(summary = "Handle successful payment redirect",
            description = "Callback endpoint for successful payment completion",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Payment confirmation message",
                            content = @Content(mediaType = "text/plain",
                                    schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400",
                            description = "Invalid session ID",
                            content = @Content),
                    @ApiResponse(responseCode = "404",
                            description = "Transaction not found",
                            content = @Content)
            })
    @GetMapping("/success")
    public ResponseEntity<String> paymentSuccess(@RequestParam("session_id") String sessionId) {
        return ResponseEntity.ok("Payment successful! Session ID: " + sessionId);
    }

    @Operation(summary = "Handle payment cancellation",
            description = "Callback endpoint for cancelled payments",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Payment cancellation confirmation",
                            content = @Content(mediaType = "text/plain",
                                    schema = @Schema(implementation = String.class)))
            })
    @GetMapping("/cancel")
    public ResponseEntity<String> paymentCancel() {
        return ResponseEntity.ok("Payment cancelled.");
    }
}