package org.example.paymentderviceaplicationii.model;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.paymentderviceaplicationii.converter.StatusConverter;
import org.example.paymentderviceaplicationii.model.enums.Status;

import java.math.BigDecimal;

@Entity
@Table(name = "payment_transaction")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class PaymentTransaction extends BaseEntity {

    private BigDecimal amount;

    private String currency;

    @Convert(converter = StatusConverter.class)
    private Status status;

    private String description;

    private String destination;

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
