package org.example.paymentderviceaplicationii.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.paymentderviceaplicationii.model.enums.PaymentProvider;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransactionRequestDTO {
    @NotNull
    private PaymentProvider paymentProvider;

    @NotNull
    private Long amount;

    @NotNull
    @Size(min = 3)
    private String currency;

    @NotBlank
    private String description;

    @NotBlank
    private String userPaymentEmail;
}
