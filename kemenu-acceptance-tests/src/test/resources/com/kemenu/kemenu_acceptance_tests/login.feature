Feature: Login
  How a user login into the application

  Scenario: An already registered user login into the application successfully
    Given A user visits HOME
    When clicks on SIGN_IN
    And puts "admin@example.com" as EMAIL and "adminPassword" as PASSWORD in login form
    And clicks on SIGN_IN_SUBMIT
    Then is redirected to "/menu"