Feature: User services
  Scenario: Create a new user
    Given I have new user data with username "kietakaktis", email "tavo@kakta", and password "VenaIsKaktos"
    When I send a POST request to "/users" to create the user
    Then the response status should be 201
    And the response should contain username "kietakaktis"
    And the response should contain email "tavo@kakta"