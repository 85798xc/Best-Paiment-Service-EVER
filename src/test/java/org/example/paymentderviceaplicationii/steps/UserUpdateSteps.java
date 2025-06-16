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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

public class UserUpdateSteps extends CucumberConfig {

    private MvcResult mvcResult;
    private UserDTO updatedUserDTO;
    private User mockUpdatedUser;

    @Given("a user with ID value of {long} exists")
    public void aUserWithIDValueOfExists(Long id){

    }

    @And("I have updated user data with username {string}, email {string}, and password {string}")
    public void iHaveUpdatedUserDataWithUsernameEmailAndPassword(String username, String email, String password) {
        updatedUserDTO = new UserDTO();
        updatedUserDTO.setUsername(username);
        updatedUserDTO.setEmail(email);
        updatedUserDTO.setPassword(password);

        mockUpdatedUser = new User();
        mockUpdatedUser.setId(9L);
        mockUpdatedUser.setUsername(username);
        mockUpdatedUser.setEmail(email);

        Mockito.when(userService.updateUser(Mockito.eq(9L), Mockito.any()))
                .thenReturn(mockUpdatedUser);
    }

    @When("I send a PUT request to {string} to update the user")
    public void iSendAPUTRequestToToUpdateTheUser(String path) throws Exception {

        String jwtToken ="eyJhbGciOiJIUzI1NiJ9.eyJpc3N1ZXIiOiJwYXltZW50LXNlcnZpY2UtYXBwIiwic3ViIjoidGVzdGFzIiwiaWF0IjoxNzUwMDcwODc2LCJleHAiOjE4MzY0NzQ0NzZ9.J-fkn1P0x44l7QHa3NGePtjnW7iJbVUbFm0c8bedDCo";

        mvcResult = mockMvc.perform(put(path)
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUserDTO)))
                .andReturn();
    }

    @Then("the response status of update request should be {int}")
    public void theResponseStatusOfUpdateRequestShouldBe(int status) {
        int actualStatus = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(status, actualStatus);
    }

    @And("the response should contain updated user with username {string}")
    public void theResponseShouldContainUpdatedUserWithUsername(String username) throws Exception {
        String responseContent = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(responseContent.contains(username));
    }

    @And("the response should contain updated user with email {string}")
    public void theResponseShouldContainUpdatedUserWithEmail(String email) throws Throwable {
        String responseContent = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(responseContent.contains(email));
    }
}
