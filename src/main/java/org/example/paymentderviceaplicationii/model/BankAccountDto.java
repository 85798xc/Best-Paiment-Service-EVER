package org.example.paymentderviceaplicationii.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class BankAccountDto {
    private String accountNumber;

    private BigDecimal balance;

    private User user;
}
