Feature: Customers can be registered and consulted

  Scenario: client makes call to GET /v1/customers
    When the client calls /v1/customers
    Then the client receives status code of 200
    And the client receives a list with all customers

  Scenario: client makes call to POST /v1/customers
    When the client calls /v1/customers with a accountNumber a name and a positive balance
    Then the client receives status code of 200
    And the client receives the registered customer with the id