package org.example.paymentderviceaplicationii.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.example.paymentderviceaplicationii.model.dto.StripeRequestDTO;
import org.example.paymentderviceaplicationii.model.dto.StripeResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class StripeServiceImpl implements StripeService {
    @Override
    public StripeResponseDTO createStripePayment(StripeRequestDTO stripeRequestDTO) throws StripeException {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(stripeRequestDTO.getAmount() * 100L)
                .putMetadata("Payment description", stripeRequestDTO.getDescription())
                .setCurrency("EUR")
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods
                                .builder()
                                .setEnabled(true)
                                .build()
                )
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        return new StripeResponseDTO(paymentIntent.getId(), paymentIntent.getClientSecret());
    }
}
