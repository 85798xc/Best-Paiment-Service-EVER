package org.example.paymentderviceaplicationii.mapper;

import lombok.RequiredArgsConstructor;
import org.example.paymentderviceaplicationii.model.PaymentTransaction;
import org.example.paymentderviceaplicationii.model.User;
import org.example.paymentderviceaplicationii.model.dto.PaymentTransactionDTO;
import org.example.paymentderviceaplicationii.model.dto.PaymentTransactionRequestDTO;
import org.example.paymentderviceaplicationii.model.enums.Status;
import org.example.paymentderviceaplicationii.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PaymentTransactionMapper {

    private final UserRepository userRepository;

    public PaymentTransaction toEntity(PaymentTransactionDTO paymentTransactionDTO, String userEmail) {
        User user = userRepository.findByEmail(userEmail);

        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setPaymentProvider(paymentTransactionDTO.getPaymentProvider());
        paymentTransaction.setAmount(paymentTransactionDTO.getAmount());
        paymentTransaction.setCurrency(paymentTransactionDTO.getCurrency());
        paymentTransaction.setStatus(paymentTransactionDTO.getStatus());
        paymentTransaction.setDescription(paymentTransactionDTO.getDescription());
        paymentTransaction.setUserPaymentEmail(paymentTransactionDTO.getUserPaymentEmail());
        paymentTransaction.setUser(user);

        return paymentTransaction;
    }

    public PaymentTransactionDTO toDTO(PaymentTransaction paymentTransaction) {
        PaymentTransactionDTO paymentTransactionDTO = new PaymentTransactionDTO();
        paymentTransactionDTO.setPaymentProvider(paymentTransaction.getPaymentProvider());
        paymentTransactionDTO.setAmount(paymentTransaction.getAmount());
        paymentTransactionDTO.setCurrency(paymentTransaction.getCurrency());
        paymentTransactionDTO.setStatus(paymentTransaction.getStatus());
        paymentTransactionDTO.setDescription(paymentTransaction.getDescription());
        paymentTransactionDTO.setUserPaymentEmail(paymentTransaction.getUserPaymentEmail());

        return paymentTransactionDTO;
    }

    public List<PaymentTransactionDTO> toDTOList(List<PaymentTransaction> paymentTransactions) {
        return paymentTransactions.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PaymentTransactionDTO fromRequestDTO(PaymentTransactionRequestDTO requestDTO, Status status) {
        PaymentTransactionDTO paymentTransactionDTO = new PaymentTransactionDTO();
        paymentTransactionDTO.setPaymentProvider(requestDTO.getPaymentProvider());
        paymentTransactionDTO.setAmount(requestDTO.getAmount());
        paymentTransactionDTO.setCurrency(requestDTO.getCurrency());
        paymentTransactionDTO.setStatus(status);
        paymentTransactionDTO.setDescription(requestDTO.getDescription());
        paymentTransactionDTO.setUserPaymentEmail(requestDTO.getUserPaymentEmail());

        return paymentTransactionDTO;
    }
}
