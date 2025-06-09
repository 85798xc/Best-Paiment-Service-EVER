package org.example.paymentderviceaplicationii.service;

import com.stripe.exception.StripeException;
import org.example.paymentderviceaplicationii.model.dto.StripeRequestDTO;
import org.example.paymentderviceaplicationii.model.dto.StripeResponseDTO;

public interface StripeService {
    StripeResponseDTO createStripePayment(StripeRequestDTO stripeRequestDTO) throws StripeException;
}
