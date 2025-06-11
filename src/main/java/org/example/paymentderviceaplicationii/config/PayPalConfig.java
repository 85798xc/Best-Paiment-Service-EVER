package org.example.paymentderviceaplicationii.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class PayPalConfig {
    @Value("${spring.paypal.client.id}")
    private String clientId;

    @Value("${spring.paypal.client.secret}")
    private String clientSecret;

    @Value("${spring.paypal.mode}") // live/sandbox
    private String mode;
}
