package org.example.paymentderviceaplicationii.service;

import org.example.paymentderviceaplicationii.model.dto.PaymentTransactionDTO;

public interface EmailSenderService {
    String sendEmail(PaymentTransactionDTO paymentTransaction);
}
