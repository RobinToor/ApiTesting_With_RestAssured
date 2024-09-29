Feature: Operations about users

  @tag1
  Scenario: Create a user
    Given The create new user payload is created with dummy data
    When The user trigger Api call "Create new user" with "Post" http request
    Then The Api call should be succeeded with status code 200
    And "type" in response body should be "unknown"

  Scenario Outline: Update user details
    Given The details of the previous user are noted
    And Update <lastname> and <email> in the request body
    When The user trigger Api call "Update user" with "Put" http request
    Then The Api call should be succeeded with status code 200
    And "type" in response body should be "unknown"
    And <lastname> and <email> should be updated

    Examples: 
      | lastname | email             |
      | "Smith"  | "smith@email.com" |

  Scenario: Delete user
    Given The details of the previous user are noted
    When The user trigger Api call "Delete user" with "Delete" http request
    Then The Api call should be succeeded with status code 200
    And "type" in response body should be "unknown"
    And "message" in response body should be "username"
    And User record should be deleted
