package org.example.paymentderviceaplicationii.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.paymentderviceaplicationii.CucumberConfig;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

public class UserDeleteSteps extends CucumberConfig {

    private MvcResult mvcResult;

    @Given("a user with ID {long} exists")
    public void aUserWithIDExists(Long id) {
        Mockito.doNothing().when(userService).deleteUser(id);
    }

    @When("I send a DELETE request to {string}")
    public void iSendADELETERequestTo(String path) throws Exception {
        String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJpc3N1ZXIiOiJwYXltZW50LXNlcnZpY2UtYXBwIiwic3ViIjoidGVzdGFzIiwiaWF0IjoxNzUwMDcwODc2LCJleHAiOjE4MzY0NzQ0NzZ9.J-fkn1P0x44l7QHa3NGePtjnW7iJbVUbFm0c8bedDCo";

        mvcResult = mockMvc.perform(delete(path)
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Then("the response status of delete request should be {int}")
    public void theResponseStatusOfDeleteRequestShouldBe(int status) {
        int actualStatus = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(status, actualStatus);
    }
}
