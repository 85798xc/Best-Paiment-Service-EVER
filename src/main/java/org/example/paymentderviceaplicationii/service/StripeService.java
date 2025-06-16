package org.example.paymentderviceaplicationii.service;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.example.paymentderviceaplicationii.kafka.KafkaPaymentTransactionProducer;
import org.example.paymentderviceaplicationii.mapper.PaymentTransactionMapper;
import org.example.paymentderviceaplicationii.model.dto.PaymentTransactionDTO;
import org.example.paymentderviceaplicationii.model.dto.StripeRequestDTO;
import org.example.paymentderviceaplicationii.model.enums.PaymentProvider;
import org.example.paymentderviceaplicationii.model.enums.Status;
import org.example.paymentderviceaplicationii.repository.PaymentTransactionRepository;
import org.example.paymentderviceaplicationii.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripeService {
    private final UserRepository userRepository;

    private final KafkaPaymentTransactionProducer kafkaPaymentTransactionProducer;

    private final TransformationService transformationService;

    private final PaymentTransactionRepository paymentTransactionRepository;

    private final PaymentTransactionMapper  paymentTransactionMapper;

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
                .setSuccessUrl("http://localhost:8080/stripe/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("http://localhost:8080/stripe/cancel?session_id={CHECKOUT_SESSION_ID}")
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

    public PaymentTransactionDTO handleStripePayment(String sessionId) throws StripeException {
        Session session = Session.retrieve(sessionId);
        PaymentTransactionDTO paymentTransaction = new PaymentTransactionDTO();

        String email = session.getCustomerEmail();
        String description = session.listLineItems().getData().getFirst().getDescription();
        String userEmail = userRepository.findByEmail(email).getEmail();
        paymentTransaction.setPaymentProvider(PaymentProvider.STRIPE);
        paymentTransaction.setUserPaymentEmail(email);
        paymentTransaction.setAmount(session.getAmountTotal() / 100);
        paymentTransaction.setCurrency(session.getCurrency());
        paymentTransaction.setDescription(description);

        if ("complete".equals(session.getStatus())) {
            paymentTransaction.setStatus(Status.SUCCESS);
        } else {
            paymentTransaction.setStatus(Status.FAILED);
        }

        kafkaPaymentTransactionProducer.send(
                transformationService.toJson(paymentTransaction)
        );

        paymentTransactionRepository.save(
                paymentTransactionMapper.toEntity(paymentTransaction, userEmail)
        );

        return  paymentTransaction;
    }
}
