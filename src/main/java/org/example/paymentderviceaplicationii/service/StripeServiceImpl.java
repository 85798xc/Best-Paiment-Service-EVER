package org.example.paymentderviceaplicationii.service;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.example.paymentderviceaplicationii.model.dto.StripeRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class StripeServiceImpl implements StripeService {
    @Override
    public String createStripePayment(StripeRequestDTO request) {
        SessionCreateParams.LineItem.PriceData.ProductData productData
                = SessionCreateParams.LineItem.PriceData.ProductData
                .builder()
                .setName(request.getDescription())
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
                .setQuantity(1L)
                .setPriceData(priceData)
                .build();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/product/v1/success?session_id={CHECKOUT_SESSION_ID}")
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
            return "Failed to create payment session";

        }

        return session.getUrl();
    }
}
