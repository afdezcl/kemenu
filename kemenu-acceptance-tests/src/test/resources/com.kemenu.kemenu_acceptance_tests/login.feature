Feature: Login
  How a user login into the application

  Scenario: An already registered user login into the application successfully
    Given An already registered user
    When clicks on
    And put email "admin@example.com" and password "adminPassword"
    And clicks on