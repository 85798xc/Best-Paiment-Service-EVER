package org.example.paymentderviceaplicationii.converter;

import org.example.paymentderviceaplicationii.model.*;
import org.springframework.stereotype.Component;

@Component
public class ToDtoConverter {
    public PaymentTransactionDTO toDto(PaymentTransaction t) {
        return new PaymentTransactionDTO(
                t.getId(),
                t.getAmount(),
                t.getCurrency(),
                t.getStatus(),
                t.getDescription(),
                t.getDestination(),
                BankAccountToDto(t.getBankAccount()),
                UserToDto(t.getUser()),
                t.getCreatedAt(),
                t.getUpdatedAt()
        );
    }

    public UserDto UserToDto(User user) {
        if (user == null) return null;

        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
                null,
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public BankAccountDto BankAccountToDto(BankAccount account) {
        if (account == null) return null;

        return new BankAccountDto(
                account.getAccountNumber(),
                account.getBalance(),
                null
        );
    }

}
