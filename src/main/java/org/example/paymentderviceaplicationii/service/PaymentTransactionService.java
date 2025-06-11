package org.example.paymentderviceaplicationii.service;

import org.example.paymentderviceaplicationii.model.dto.PaymentTransactionRequestDTO;

public interface PaymentTransactionService {
    String createPaymentTransaction(PaymentTransactionRequestDTO request);
}
