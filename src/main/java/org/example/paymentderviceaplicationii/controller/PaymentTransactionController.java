package org.example.paymentderviceaplicationii.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.paymentderviceaplicationii.kafka.KafkaPaymentTransactionProducer;
import org.example.paymentderviceaplicationii.mapper.PaymentTransactionMapper;
import org.example.paymentderviceaplicationii.model.dto.PaymentTransactionDTO;
import org.example.paymentderviceaplicationii.model.dto.PaymentTransactionRequestDTO;
import org.example.paymentderviceaplicationii.model.enums.Status;
import org.example.paymentderviceaplicationii.service.PaymentTransactionService;
import org.example.paymentderviceaplicationii.service.TransformationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Tag(name = "Payment Transactions", description = "Endpoints for managing payment transactions")
public class PaymentTransactionController {

    private final PaymentTransactionService paymentTransactionService;

    private final KafkaPaymentTransactionProducer paymentTransactionProducer;

    private final PaymentTransactionMapper paymentTransactionMapper;

    private final TransformationService transformationService;

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
        String paymentUrl = paymentTransactionService.createPaymentTransaction(request);

        paymentTransactionProducer.send(
                transformationService.toJson(
                        paymentTransactionMapper.fromRequestDTO(request, Status.PROCESSING)
                )
        );

        return ResponseEntity.ok(paymentUrl);
    }

    @Operation(summary = "Gets all user transactions",
            description = "Gets all user transactions",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "User transactions list",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400",
                            description = "User not found",
                            content = @Content),
                    @ApiResponse(responseCode = "500",
                            description = "Internal server error during payment processing",
                            content = @Content)
            })
    @GetMapping("/transactions")
    public List<PaymentTransactionDTO> getAllTransactions() {
        return paymentTransactionService.getAllUserTransactions();
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