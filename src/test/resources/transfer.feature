Feature: Customers can transfer balance between them

  Scenario Outline: client makes call to POST /v1/transfers
    When the client calls /v1/transfers with a existing <originAccount> and a existing <destinationAccount> and a positive <value> under thousand
    Then the client receives status transfer code of 200
    And the client receives the registered transfer with the id

    Examples:
      | originAccount  | destinationAccount  |  value  |
      | 1001           | 1002                |  1000   |
      | 1001           | 1002                |  10     |
      | 1002           | 1001                |  100    |
      | 1002           | 1001                |  10     |

  Scenario Outline: client makes call to POST /v1/transfers
    When the client calls /v1/transfers with <originAccount> and <destinationAccount> and a negative <value>
    Then the client receives status transfer code of 400

    Examples:
      | originAccount  | destinationAccount  |  value  |
      | 1001           | 1002                |  -1     |

  Scenario Outline: client makes call to POST /v1/transfers
    When the client calls /v1/transfers with <originAccount> and <destinationAccount> and <value> greater than
    Then the client receives status transfer code of 400

    Examples:
      | originAccount  | destinationAccount  |  value  |
      | 1001           | 1002                |   1200  |

  Scenario Outline: client makes call to POST /v1/transfers
    When the client calls /v1/transfers with <originAccount> or <destinationAccount> not existing and <value>
    Then the client receives status transfer code of 404

    Examples:
      | originAccount  | destinationAccount  |  value  |
      | 777            | 1002                |  100    |

  Scenario Outline: client makes call to POST /v1/transfers
    When the client calls /v1/transfers with <originAccount> negative balance and <destinationAccount> and <value>
    Then the client receives status transfer code of 400

    Examples:
      | originAccount  | destinationAccount  |  value  |
      | 102            | 1002                |  1000    |

  Scenario Outline: client makes call to GET /v1/transfers/account
    When the client calls /v1/transfers/account with a existing <accountNumber>
    Then the client receives status transfer code of 200
    And the client receives transfers list

    Examples:
      | accountNumber |
      | 101           |

