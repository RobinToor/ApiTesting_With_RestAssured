Feature: Operations about users

  @tag1
  Scenario: Create a user
    Given The create new user payload is created with dummy data
    When The user trigger Api call "Create new user" with "Post" http request
    Then The Api call should be succeeded with status code 200
    And "status" in response body should be "OK"

  Scenario: Update user details
    Given The details of the previous user are noted
    And Update <lastname> and <email>
    When The user trigger Api call "Create new user" with "Post" http request
