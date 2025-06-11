package org.example.paymentderviceaplicationii.service;

import lombok.RequiredArgsConstructor;
import org.example.paymentderviceaplicationii.config.PayPalConfig;
import org.example.paymentderviceaplicationii.model.dto.PayPalRequestDTO;
import org.example.paymentderviceaplicationii.model.dto.PayPalResponseDTO;
import org.example.paymentderviceaplicationii.model.dto.PaymentTransactionRequestDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PayPalService {

    private final PayPalConfig payPalConfig;
    private final RestTemplate restTemplate = new RestTemplate();


    private String getAccessToken() {
        String clientId = payPalConfig.getClientId();
        String clientSecret = payPalConfig.getClientSecret();

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>("grant_type=client_credentials", headers);

        String paypalApiBase = payPalConfig.getMode().equalsIgnoreCase("sandbox")
                ? "https://api-m.sandbox.paypal.com"
                : "https://api-m.paypal.com";

        ResponseEntity<Map> response = restTemplate.exchange(
                paypalApiBase + "/v1/oauth2/token",
                HttpMethod.POST,
                entity,
                Map.class
        );

        return response.getBody().get("access_token").toString();
    }

    public String createPayPalOrder(PayPalRequestDTO requestDTO) {
        String accessToken = getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> orderRequest = new HashMap<>();
        orderRequest.put("intent", "CAPTURE");
        Map<String, Object> purchaseUnit = new HashMap<>();
        purchaseUnit.put("amount", Map.of(
                "currency_code", "EUR",
                "value", requestDTO.getAmount().toString()
        ));
        purchaseUnit.put("description", requestDTO.getDescription());
        orderRequest.put("purchase_units", List.of(purchaseUnit));
        orderRequest.put("application_context", Map.of(
                "return_url", "https://example.com/return",
                "cancel_url", "https://example.com/cancel"
        ));

        String paypalApiBase = payPalConfig.getMode().equalsIgnoreCase("sandbox")
                ? "https://api-m.sandbox.paypal.com"
                : "https://api-m.paypal.com";

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(orderRequest, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                paypalApiBase + "/v2/checkout/orders",
                entity,
                Map.class
        );

        return ((List<Map<String, Object>>) response.getBody().get("links"))
                .stream()
                .filter(link -> "approve".equals(link.get("rel")))
                .findFirst()
                .map(link -> link.get("href").toString())
                .orElse(null);

    }
}
