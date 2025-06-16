package org.example.paymentderviceaplicationii.controller;

import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.example.paymentderviceaplicationii.model.dto.PaymentTransactionDTO;
import org.example.paymentderviceaplicationii.service.EmailSenderService;
import org.example.paymentderviceaplicationii.service.StripeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PaymentTransactionSessionController {

    private final EmailSenderService emailSenderService;

    private final StripeService stripeService;

    @Operation(summary = "Handle successful payment redirect")
    @ApiResponse(responseCode = "200",
            description = "HTML success page returned",
            content = @Content(mediaType = "text/html"))
    @GetMapping("/stripe/success")
    public String getSession(@RequestParam("session_id") String sessionId) throws StripeException {
        PaymentTransactionDTO paymentTransaction =
                stripeService.handleStripePayment(sessionId);

        emailSenderService.sendEmail(paymentTransaction);

        return "success";
    }

    @Operation(summary = "Handle failed payment redirect")
    @ApiResponse(responseCode = "200",
            description = "HTML failed page returned",
            content = @Content(mediaType = "text/html"))
    @GetMapping("/stripe/failed")
    public String getFailedSession(@RequestParam("session_id") String sessionId) throws StripeException {
        PaymentTransactionDTO paymentTransaction =
                stripeService.handleStripePayment(sessionId);

        return "failed";
    }

}
