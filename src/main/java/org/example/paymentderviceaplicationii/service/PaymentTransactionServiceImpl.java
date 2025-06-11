package org.example.paymentderviceaplicationii.service;

import lombok.RequiredArgsConstructor;
import org.example.paymentderviceaplicationii.exception.PaymentTypeNotFoundException;
import org.example.paymentderviceaplicationii.model.dto.PayPalRequestDTO;
import org.example.paymentderviceaplicationii.model.dto.PaymentTransactionRequestDTO;
import org.example.paymentderviceaplicationii.model.dto.StripeRequestDTO;
import org.example.paymentderviceaplicationii.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PaymentTransactionServiceImpl implements PaymentTransactionService {

    private final PayPalService payPalService;

    private final StripeService stripeService;

    private final UserRepository userRepository;

    public String createPaymentTransaction(
            PaymentTransactionRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        String email = userRepository.findByUsername(username).getEmail();

        switch (request.getPaymentProvider()) {
            case PAYPAL:
                PayPalRequestDTO payPalRequestDTO = new PayPalRequestDTO();
                payPalRequestDTO.setEmail(email);
                payPalRequestDTO.setAmount(request.getAmount());
                payPalRequestDTO.setDescription(request.getDescription());

                return payPalService.createPayPalOrder(payPalRequestDTO);
            case STRIPE:
                StripeRequestDTO stripeRequestDTO = new StripeRequestDTO();
                stripeRequestDTO.setEmail(email);
                stripeRequestDTO.setAmount(request.getAmount());
                stripeRequestDTO.setCurrency(request.getCurrency());
                stripeRequestDTO.setDescription(request.getDescription());

                return stripeService.createStripePayment(stripeRequestDTO);
            default:
                throw new PaymentTypeNotFoundException();
        }
    }
}
