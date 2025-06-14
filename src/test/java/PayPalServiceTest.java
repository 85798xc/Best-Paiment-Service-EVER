import org.example.paymentderviceaplicationii.config.PayPalConfig;
import org.example.paymentderviceaplicationii.model.dto.PayPalRequestDTO;
import org.example.paymentderviceaplicationii.service.PayPalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;



public class PayPalServiceTest {

    @Mock
    private PayPalConfig payPalConfig;

    @Mock
    private RestTemplate mockRestTemplate;

    @InjectMocks
    private PayPalService payPalService;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        payPalService = new PayPalService(payPalConfig);

        Field restTemplateField = PayPalService.class.getDeclaredField("restTemplate");
        restTemplateField.setAccessible(true);
        restTemplateField.set(payPalService, mockRestTemplate);
    }

    @Test
    void testCreatePayPalOrder_ReturnsApprovalLink() {
        PayPalRequestDTO requestDTO = new PayPalRequestDTO(20L, "user@example.com", "Test order");

        when(payPalConfig.getClientId()).thenReturn("mock-client-id");
        when(payPalConfig.getClientSecret()).thenReturn("mock-client-secret");
        when(payPalConfig.getMode()).thenReturn("sandbox");

        Map<String, Object> tokenBody = Map.of("access_token", "mocked_token");
        ResponseEntity<Map> tokenResponse = new ResponseEntity<>(tokenBody, HttpStatus.OK);
        when(mockRestTemplate.exchange(
                eq("https://api-m.sandbox.paypal.com/v1/oauth2/token"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(Map.class)
        )).thenReturn(tokenResponse);

        Map<String, Object> approveLink = Map.of("rel", "approve", "href", "https://paypal.com/approve");
        Map<String, Object> orderBody = Map.of("links", List.of(approveLink));
        ResponseEntity<Map> orderResponse = new ResponseEntity<>(orderBody, HttpStatus.CREATED);
        when(mockRestTemplate.postForEntity(
                eq("https://api-m.sandbox.paypal.com/v2/checkout/orders"),
                any(HttpEntity.class),
                eq(Map.class)
        )).thenReturn(orderResponse);


        String result = payPalService.createPayPalOrder(requestDTO);

        assertEquals("https://paypal.com/approve", result);
    }
    @Test
    void testCreatePayPalOrder_NoApproveLink_ReturnsNull() {
        PayPalRequestDTO requestDTO = new PayPalRequestDTO(20L, "user@example.com", "Test order");

        when(payPalConfig.getClientId()).thenReturn("mock-client-id");
        when(payPalConfig.getClientSecret()).thenReturn("mock-client-secret");
        when(payPalConfig.getMode()).thenReturn("sandbox");

        Map<String, Object> tokenBody = Map.of("access_token", "mocked_token");
        ResponseEntity<Map> tokenResponse = new ResponseEntity<>(tokenBody, HttpStatus.OK);
        when(mockRestTemplate.exchange(
                anyString(),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(Map.class)
        )).thenReturn(tokenResponse);

        Map<String, Object> link1 = Map.of("rel", "self", "href", "https://paypal.com/self");
        Map<String, Object> orderBody = Map.of("links", List.of(link1));
        ResponseEntity<Map> orderResponse = new ResponseEntity<>(orderBody, HttpStatus.CREATED);
        when(mockRestTemplate.postForEntity(
                anyString(),
                any(HttpEntity.class),
                eq(Map.class)
        )).thenReturn(orderResponse);

        String result = payPalService.createPayPalOrder(requestDTO);

        assertNull(result);
    }
    @Test
    void testPayPalRequestDTO_InvalidAmount_ShouldFailValidation() {
        PayPalRequestDTO dto = new PayPalRequestDTO(0L, "user@example.com", "Valid description");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<PayPalRequestDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("amount")));
    }

    @Test
    void testPayPalRequestDTO_InvalidEmail_ShouldFailValidation() {
        PayPalRequestDTO dto = new PayPalRequestDTO(20L, "not-an-email", "Valid description");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<PayPalRequestDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void testPayPalRequestDTO_BlankDescription_ShouldFailValidation() {
        PayPalRequestDTO dto = new PayPalRequestDTO(20L, "user@example.com", "   ");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<PayPalRequestDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("description")));
    }



}