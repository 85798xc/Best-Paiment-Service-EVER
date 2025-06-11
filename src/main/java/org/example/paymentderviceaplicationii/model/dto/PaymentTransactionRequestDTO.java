package org.example.paymentderviceaplicationii.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.paymentderviceaplicationii.model.enums.PaymentProvider;
import org.example.paymentderviceaplicationii.model.enums.Status;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentTransactionRequestDTO {
    @NotNull
    private PaymentProvider paymentProvider;

    @NotNull
    private Long amount;

    @NotNull
    @Min(3)
    private String currency;

    @NotNull
    private Status status;

    @NotBlank
    private String description;

    private String userPaymentEmail;
}
