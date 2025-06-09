package org.example.paymentderviceaplicationii.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StripeResponseDTO {
    private String paymentIntentId;

    private String clientSecret;
}
