package org.example.paymentderviceaplicationii.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.paymentderviceaplicationii.model.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentTransactionDTO {
    private Long id;
    private BigDecimal amount;
    private String currency;
    private Status status;
    private String description;
    private String destination;
    private BankAccountDto bankAccount;
    private UserDto user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
