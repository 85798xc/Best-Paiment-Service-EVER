package org.example.paymentderviceaplicationii.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.paymentderviceaplicationii.CucumberConfig;
import org.example.paymentderviceaplicationii.model.User;
import org.example.paymentderviceaplicationii.model.dto.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class UserSteps extends CucumberConfig {

    private MvcResult mvcResult;
    private UserDTO testUserDTO;
    private User mockUser;

    @Given("I have new user data with username {string}, email {string}, and password {string}")
    public void i_have_new_user_data(String username, String email, String password) {
        testUserDTO = new UserDTO();
        testUserDTO.setUsername(username);
        testUserDTO.setEmail(email);
        testUserDTO.setPassword(password);

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername(username);
        mockUser.setEmail(email);

        Mockito.when(userService.createUser(Mockito.any())).thenReturn(mockUser);
    }

    @When("I send a POST request to {string} to create the user")
    public void i_send_post_request(String path) throws Exception {
        String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJpc3N1ZXIiOiJwYXltZW50LXNlcnZpY2UtYXBwIiwic3ViIjoidGVzdGFzIiwiaWF0IjoxNzUwMDcwODc2LCJleHAiOjE4MzY0NzQ0NzZ9.J-fkn1P0x44l7QHa3NGePtjnW7iJbVUbFm0c8bedDCo";
        mvcResult = mockMvc.perform(post(path)
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUserDTO)))
                .andReturn();
    }

    @Then("the response status should be {int}")
    public void response_status_should_be(int status) {
        int actualStatus = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(status, actualStatus);
    }

    @And("the response should contain username {string}")
    public void response_should_contain_username(String expectedUsername) throws Exception {
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(jsonResponse.contains(expectedUsername));
    }

    @And("the response should contain email {string}")
    public void response_should_contain_email(String expectedEmail) throws Exception {
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(jsonResponse.contains(expectedEmail));
    }
}
