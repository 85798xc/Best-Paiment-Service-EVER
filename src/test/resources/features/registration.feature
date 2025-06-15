Feature: User Registration

  Scenario: Successful user registration
    Given the registration payload with email "test@example.com", username "testuser123" and password "password123"
    When the client sends a POST request to "/auth/register"
    Then the response status should be 200
    And the response should contain a JWT token

  Scenario: Registration with existing email
    Given an existing user with email "existing@example.com", username "existinguser" and password "password123"
    And the registration payload with email "existing@example.com", username "existinguser" and password "password123"
    When the client sends a POST request to "/auth/register"
    Then the response status should be 401
