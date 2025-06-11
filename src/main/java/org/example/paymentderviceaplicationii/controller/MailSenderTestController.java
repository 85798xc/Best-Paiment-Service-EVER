package org.example.paymentderviceaplicationii.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.paymentderviceaplicationii.model.dto.PaymentTransactionDTO;
import org.example.paymentderviceaplicationii.service.EmailSenderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail-sender-test")
@RequiredArgsConstructor
@Slf4j
public class MailSenderTestController {
    private final EmailSenderService emailSenderService;

    @PostMapping
    public String sentEmail(@RequestBody PaymentTransactionDTO paymenttransactionDTO) {
        log.info("Sending email to {}", paymenttransactionDTO.getDestination());

        return emailSenderService.sendEmail(paymenttransactionDTO);
    }
}
