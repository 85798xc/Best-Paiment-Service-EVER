package org.example.paymentderviceaplicationii.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.example.paymentderviceaplicationii.model.dto.StripeRequestDTO;
import org.example.paymentderviceaplicationii.model.dto.StripeResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class StripeServiceImpl implements StripeService {
    @Override
    public StripeResponseDTO createStripePayment(StripeRequestDTO request) {
        SessionCreateParams.LineItem.PriceData.ProductData productData
                = SessionCreateParams.LineItem.PriceData.ProductData
                .builder()
                .setName(request.getProductName())
                .setDescription("Payment description - " + request.getDescription()
                ).build();

        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData
                .builder()
                .setCurrency(request.getCurrency() == null ? "EUR" : request.getCurrency())
                .setUnitAmount(request.getAmount() * 100)
                .setProductData(productData)
                .build();

        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem
                .builder()
                .setQuantity(request.getQuantity())
                .setPriceData(priceData)
                .build();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/product/v1/success")
                .setCancelUrl("http://localhost:8080/cancel")
                .addLineItem(lineItem)
                .setCustomerEmail(request.getEmail())
                .setPaymentIntentData(
                        SessionCreateParams.PaymentIntentData.builder()
                                .putMetadata("Payment description", request.getDescription())
                                .putMetadata("Customer email", request.getEmail())
                                .build())
                .build();

        Session session;

        try {
            session = Session.create(params);
        } catch (StripeException e) {
            return StripeResponseDTO.builder()
                    .status("ERROR")
                    .message("Failed to create payment session: " + e.getMessage())
                    .build();
        }
        return StripeResponseDTO.builder()
                .status("SUCCESS")
                .message("Payment session created").
                sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();
    }
}
