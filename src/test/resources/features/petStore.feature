Feature: Operations about PetStore
  I want to use this template for my feature file

   @tag1
  Scenario: Add a new pet to the store
    Given The Add new pet to the store payload is created with dummy data
    When The user trigger Api call "Add new pet" with "Post" http request
    Then The Api call should be succeeded with status code 200
    And "name" in response body should be "petname"
    And "status" in response body should be "available"
