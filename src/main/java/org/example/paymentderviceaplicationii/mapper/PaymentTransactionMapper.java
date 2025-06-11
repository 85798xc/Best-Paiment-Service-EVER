package org.example.paymentderviceaplicationii.mapper;

import lombok.RequiredArgsConstructor;
import org.example.paymentderviceaplicationii.model.PaymentTransaction;
import org.example.paymentderviceaplicationii.model.User;
import org.example.paymentderviceaplicationii.model.dto.PaymentTransactionDTO;
import org.example.paymentderviceaplicationii.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentTransactionMapper {

    private final UserRepository userRepository;

    public PaymentTransaction toEntity(PaymentTransactionDTO paymentTransactionDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userRepository.findByUsername(username);

        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setAmount(paymentTransactionDTO.getAmount());
        paymentTransaction.setCurrency(paymentTransactionDTO.getCurrency());
        paymentTransaction.setStatus(paymentTransactionDTO.getStatus());
        paymentTransaction.setDescription(paymentTransactionDTO.getDescription());
        paymentTransaction.setUserPaymentEmail(paymentTransactionDTO.getUserPaymentEmail());
        paymentTransaction.setUser(user);


        return paymentTransaction;
    }
}
