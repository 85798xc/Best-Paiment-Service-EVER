Feature: Login
  Scenario: With valid credentials
    Given a user exists with email "opa@1" and password "saugus"
    When I POST to "/auth/login" with email "opa@1" and password "saugus"
    Then I should receive a 200 status and a non-empty JWT token

  Scenario: With invalid password
    Given a user exists with email "opa@1" and password "nesaugus"
    When I POST to "/auth/login" with email "opa@1" and password "nesaugus"
    Then I should receive a 401 status
