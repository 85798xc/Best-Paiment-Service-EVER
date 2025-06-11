package org.example.paymentderviceaplicationii.service;

import org.example.paymentderviceaplicationii.model.dto.StripeRequestDTO;
import org.example.paymentderviceaplicationii.model.dto.StripeResponseDTO;

public interface StripeService {
    String createStripePayment(StripeRequestDTO stripeRequestDTO);
}
