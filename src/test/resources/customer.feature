Feature: Customers can be registered and consulted

  Scenario: client makes call to GET /v1/customers
    When the client calls /v1/customers
    Then the client receives status code of 200
    And the client receives a list with all customers

  Scenario Outline: client makes call to POST /v1/customers
    When the client calls /v1/customers with <accountNumber> and <name> and <balance>
    Then the client receives status code of 201
    And the client receives the registered customer with the id

    Examples:
      | accountNumber | name      | balance  |
      | 101           |'Antonio'  | 100      |
      | 102           |'Maria'    | 0        |

  Scenario Outline: client makes call to POST /v1/customers
    When the client calls /v1/customers with <accountNumber> and <name> and a negative <balance>
    Then the client receives status code of 422

    Examples:
      | accountNumber | name      | balance |
      | 103           |'Antonio'  |-1       |

  Scenario Outline: client makes call to POST /v1/customers
    When the client calls /v1/customers with <accountNumber> already exists and <name> and a <balance>
    Then the client receives status code of 422

    Examples:
      | accountNumber | name      | balance |
      | 101           |'Antonio'  |100      |

  Scenario Outline: client makes call to GET /v1/customers/account
    When the client calls /v1/customers/account with <accountNumber>
    Then the client receives status code of 200
    And the client receives body customer

    Examples:
      | accountNumber |
      | 101           |
      | 102           |

  Scenario Outline: client makes call to GET /v1/customers/account
    When the client calls /v1/customers/account with <accountNumber>
    Then the client receives status code of 404

    Examples:
      | accountNumber |
      | 777           |
      | 888           |
      | 999           |




