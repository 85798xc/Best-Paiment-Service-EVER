package org.example.paymentderviceaplicationii;

import org.example.paymentderviceaplicationii.service.UserService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockUsersConfiq {
    @Bean
    public UserService userService() {
        return Mockito.mock(UserService.class);
    }
}
