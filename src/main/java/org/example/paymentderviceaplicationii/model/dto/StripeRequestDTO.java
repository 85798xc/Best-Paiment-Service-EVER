package org.example.paymentderviceaplicationii.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StripeRequestDTO {
    @Email
    private String email;

    @NotNull
    @Min(1)
    private Long amount;

    @NotBlank
    @Size(min = 1, max = 3)
    private String currency;

    @NotBlank
    @Size(min = 4)
    private String description;
}
