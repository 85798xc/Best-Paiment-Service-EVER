package org.example.paymentderviceaplicationii.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayPalResponseDTO {
    private String orderId;
    private String approvalLink;
}
