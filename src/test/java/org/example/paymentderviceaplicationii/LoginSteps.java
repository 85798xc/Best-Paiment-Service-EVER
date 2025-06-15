package org.example.paymentderviceaplicationii;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.paymentderviceaplicationii.model.dto.LoginDTO;
import org.example.paymentderviceaplicationii.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoginSteps extends CucumberSpringConfig {

    @Autowired
    private UserRepository userRepository;

    private String email;
    private String password;
    private ResponseEntity<String> response;

    @Given("a user exists with email {string} and password {string}")
    public void a_user_exists_with_email_and_password(String email, String password) {
        this.email = email;
        this.password = password;
        assertNotNull(userRepository.findByEmail(email), "User not found");
    }

    @When("I POST to {string} with email {string} and password {string}")
    public void i_post_to_auth_login_with_email_and_password(String url, String email, String password) {
        String fullUrl = "http://localhost:" + port + url;

        LoginDTO loginDTO = new LoginDTO(email, password);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginDTO> request = new HttpEntity<>(loginDTO, headers);

        try {
            response = restTemplate.postForEntity(fullUrl, request, String.class);
        } catch (HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }catch (Exception e) {
            response = null;
        }
    }

    @Then("I should receive a 200 status and a non-empty JWT token")
    public void i_should_receive_a_200_status_and_a_non_empty_jwt_token() {
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Then("I should receive a 401 status")
    public void i_should_receive_a_401_status() {
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
