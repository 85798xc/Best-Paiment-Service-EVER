package org.example.paymentderviceaplicationii.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.paymentderviceaplicationii.CucumberConfig;
import org.example.paymentderviceaplicationii.model.User;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class GetUsersSteps extends CucumberConfig {

    private MvcResult mvcResult;

    private List<User>mockUserList;

    @Given("there are two users in the system")
    public void thereAreUsersInTheSystem() {
        User user = new User();
        user.setId(1L);
        user.setUsername("tomas");
        user.setEmail("tomas@nesakysiu");
        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("lukas");
        user2.setEmail("lukas@nesakysiu");
        mockUserList = List.of(user, user2);

        Mockito.when(userService.getAllUsers()).thenReturn(mockUserList);
    }

    @When("I send a GET request to {string}")
    public void iSendAGETRequestTo(String path) throws Exception {
        String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJpc3N1ZXIiOiJwYXltZW50LXNlcnZpY2UtYXBwIiwic3ViIjoidGVzdGFzIiwiaWF0IjoxNzUwMDcwODc2LCJleHAiOjE4MzY0NzQ0NzZ9.J-fkn1P0x44l7QHa3NGePtjnW7iJbVUbFm0c8bedDCo";
        mvcResult = mockMvc.perform(get(path)
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Then("the response status should be a {int}")
    public void theResponseStatusShouldBeA(int status) {
        int actualStatus = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(status, actualStatus);
    }

    @And("the response should contain user with username {string} of user list")
    public void theResponseShouldContainUserWithUsername(String username) throws Exception {
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(jsonResponse.contains(username));
    }

    @And("the response should contain user with email {string} of user list")
    public void theResponseShouldContainUserWithEmail(String email) throws Exception {
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(jsonResponse.contains(email));
    }

}
