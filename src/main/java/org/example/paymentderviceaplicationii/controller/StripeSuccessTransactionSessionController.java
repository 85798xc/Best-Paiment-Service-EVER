package org.example.paymentderviceaplicationii.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import lombok.RequiredArgsConstructor;
import org.example.paymentderviceaplicationii.model.dto.PaymentTransactionRequestDTO;
import org.example.paymentderviceaplicationii.model.enums.Status;
import org.example.paymentderviceaplicationii.service.EmailSenderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StripeSuccessTransactionSessionController {

    public final EmailSenderService emailSenderService;

    @GetMapping("/product/v1/success")
    public void getSession(@RequestParam("session_id") String sessionId) throws StripeException {
        Session session = Session.retrieve(sessionId);

        if ("complete".equals(session.getStatus())) {
            String email = session.getCustomerEmail();
            String description = session.listLineItems().getData().getFirst().getDescription();

            PaymentTransactionRequestDTO request = new PaymentTransactionRequestDTO();
            request.setUserPaymentEmail(email);
            request.setAmount(session.getAmountTotal() / 100);
            request.setCurrency("EUR");
            request.setStatus(Status.SUCCESS);
            request.setDescription(description);

            emailSenderService.sendEmail(request);
        }
    }

}
