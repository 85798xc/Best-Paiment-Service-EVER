package org.example.paymentderviceaplicationii.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.paymentderviceaplicationii.model.dto.PaymentTransactionDTO;
import org.example.paymentderviceaplicationii.service.TransformationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class KafkaPaymentTransactionConsumer {
//    private final TransformationService transformationService;
//
//    @KafkaListener(topics = "payment-transaction-result", groupId = "payment_transaction_result_id")
//    public void consume(String paymentTransaction) {
//        PaymentTransactionDTO dto = transformationService.fromJson(paymentTransaction, PaymentTransactionDTO.class);
//        log.info("Payment Transaction Status: {}", dto.getStatus().toString());
//    }
//}
