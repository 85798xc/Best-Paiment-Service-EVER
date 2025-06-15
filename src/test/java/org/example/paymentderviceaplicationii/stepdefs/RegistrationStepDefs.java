package org.example.paymentderviceaplicationii.stepdefs;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistrationStepDefs {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DataSource dataSource;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity<String> request;
    private ResponseEntity<String> response;

    private String email;

    @Before
    public void truncateUsersTable() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM users");
            ps.executeUpdate();
        }
    }

    @Given("the registration payload with email {string}, username {string} and password {string}")
    public void the_registration_payload(String email, String username, String password) {
        this.email = email;

        headers.setContentType(MediaType.APPLICATION_JSON);
        String payload = String.format("""
                {
                  "email": "%s",
                  "username": "%s",
                  "password": "%s"
                }
                """, email, username, password);
        request = new HttpEntity<>(payload, headers);
    }

    @Given("an existing user with email {string}, username {string} and password {string}")
    public void an_existing_user_with_email(String email, String username, String password) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        String userPayload = String.format("""
            {
              "email": "%s",
              "username": "%s",
              "password": "%s"
            }
            """, email, username, password);
        HttpEntity<String> userRequest = new HttpEntity<>(userPayload, headers);
        restTemplate.postForEntity("/auth/register", userRequest, String.class);
    }

    @When("the client sends a POST request to {string}")
    public void the_client_sends_a_post_request_to(String endpoint) {
        response = restTemplate.postForEntity(endpoint, request, String.class);
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(Integer statusCode) {
        assertEquals(statusCode.intValue(), response.getStatusCodeValue());
    }

    @Then("the response should contain a JWT token")
    public void the_response_should_contain_a_jwt_token() {
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("."));
    }
}
