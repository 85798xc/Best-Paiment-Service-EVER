package org.example.paymentderviceaplicationii.model;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.paymentderviceaplicationii.converter.PaymentProviderConverter;
import org.example.paymentderviceaplicationii.converter.StatusConverter;
import org.example.paymentderviceaplicationii.model.enums.PaymentProvider;
import org.example.paymentderviceaplicationii.model.enums.Status;

@Entity
@Table(name = "payment_transaction")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentTransaction extends BaseEntity {
    @Convert(converter = PaymentProviderConverter.class)
    private PaymentProvider paymentProvider;

    private Long amount;

    private String currency;

    @Convert(converter = StatusConverter.class)
    private Status status;

    private String description;

    private String userPaymentEmail;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
