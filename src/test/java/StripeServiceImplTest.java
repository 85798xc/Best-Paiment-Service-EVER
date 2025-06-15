import com.stripe.exception.ApiException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.example.paymentderviceaplicationii.model.dto.StripeRequestDTO;
import org.example.paymentderviceaplicationii.service.StripeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StripeServiceImplTest {

    private StripeServiceImpl stripeService;

    @BeforeEach
    void setUp() {
        stripeService = new StripeServiceImpl();
    }

    @Test
    void testCreateStripePayment_returnsSessionUrl() throws StripeException {
        StripeRequestDTO request = new StripeRequestDTO("test@example.com", 20L, "EUR", "Test Product");

        Session mockSession = Mockito.mock(Session.class);
        Mockito.when(mockSession.getUrl()).thenReturn("https://mock-stripe-session-url");

        try (MockedStatic<Session> mockedStatic = Mockito.mockStatic(Session.class)) {
            mockedStatic.when(() -> Session.create(Mockito.any(SessionCreateParams.class)))
                    .thenReturn(mockSession);

            String result = stripeService.createStripePayment(request);

            assertEquals("https://mock-stripe-session-url", result);
        }
    }

    @Test
    void testCreateStripePayment_whenStripeThrowsException_returnsFailureMessage() throws StripeException {
        StripeRequestDTO request = new StripeRequestDTO("test@example.com", 20L, "EUR", "Test Product");

        try (MockedStatic<Session> mockedStatic = Mockito.mockStatic(Session.class)) {
            mockedStatic.when(() -> Session.create(Mockito.any(SessionCreateParams.class)))
                    .thenThrow(new ApiException("Stripe API error", null, null, 500, null));

            String result = stripeService.createStripePayment(request);

            assertEquals("Failed to create payment session", result);
        }
    }
}
