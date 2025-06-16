Feature: User services
  Scenario: Create a new user
    Given I have new user data with username "kietakaktis", email "tavo@kakta", and password "VenaIsKaktos"
    When I send a POST request to "/users" to create the user
    Then the response status should be 201
    And the response should contain username "kietakaktis"
    And the response should contain email "tavo@kakta"

  Scenario:Get all users list
    Given there are two users in the system
    When I send a GET request to "/users"
    Then the response status should be a 200
    And the response should contain user with username "tomas" of user list
    And the response should contain user with email "tomas@nesakysiu" of user list
    And the response should contain user with username "lukas" of user list
    And the response should contain user with email "lukas@nesakysiu" of user list

  Scenario:Delete a user by ID
    Given a user with ID 9 exists
    When I send a DELETE request to "/users/9"
    Then the response status of delete request should be 204

  Scenario: Update a user by ID
    Given a user with ID value of 9 exists
    And I have updated user data with username "homer", email "homer@simpson.com", and password "lovesbeer"
    When I send a PUT request to "/users/9" to update the user
    Then the response status of update request should be 200
    And the response should contain updated user with username "homer"
    And the response should contain updated user with email "homer@simpson.com"