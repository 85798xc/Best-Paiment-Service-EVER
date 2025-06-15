package org.example.paymentderviceaplicationii.controller.debug;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.paymentderviceaplicationii.model.dto.PaymentTransactionDTO;
import org.example.paymentderviceaplicationii.model.enums.PaymentProvider;
import org.example.paymentderviceaplicationii.service.EmailSenderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail-sender-test")
@RequiredArgsConstructor
@Slf4j
public class MailSenderTestController {
    private final EmailSenderService emailSenderService;

    @PostMapping
    public String sentEmail() {
        log.info("Sending email to {}", "imatveyadam@gmail.com");

        PaymentTransactionDTO request = new PaymentTransactionDTO();
        request.setPaymentProvider(PaymentProvider.STRIPE);
        request.setUserPaymentEmail("imatveyadam@gmail.com");
        request.setAmount(100L);
        request.setCurrency("EUR");
        request.setDescription("Payment description: test");

        return emailSenderService.sendEmail(request);
    }
}
