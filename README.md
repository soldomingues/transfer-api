# transfer-api

API that allows the registration of new accounts and the transfer between them.

Macro Architecture:

![Architecture](/images/arq.png)


Documentation available at: http://localhost:8080/swagger-iu.html

Request Examples:

```
  Scenario: client makes call to GET /v1/customers
    When the client calls /v1/customers
    Then the client receives status code of 200
    And the client receives a list with all customers
```

```json
curl -X GET "http://localhost:8080/v1/customers" -H  "accept: application/json"
```

```json
{
  "result": [
    {
      "id": 1,
      "accountNumber": 1001,
      "name": "Maria",
      "balance": 10000
    },
    {
      "id": 2,
      "accountNumber": 1002,
      "name": "Julia",
      "balance": 20000
    }
  ],
  "situation": {
    "date": "2020-12-01T23:38:13.247+00:00",
    "codigo": 200,
    "message": "success"
  }
}
```

```
  Scenario : client makes call to POST /v1/customers
    When the client calls /v1/customers with <accountNumber> and <name> and <balance>
    Then the client receives status code of 201
    And the client receives the registered customer with the id
```

```json
curl -X POST "http://localhost:8080/v1/customers" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{\"accountNumber\":207,\"name\":\"Luiz\",\"balance\":100}"
```

```json
{
  "result": {
    "id": 3,
    "accountNumber": 207,
    "name": "Luiz",
    "balance": 100
  },
  "situation": {
    "date": "2020-12-01T23:41:45.956+00:00",
    "codigo": 201,
    "message": "success"
  }
}
```  

```
  Scenario: client makes call to POST /v1/customers
    When the client calls /v1/customers with <accountNumber> and <name> and a negative <balance>
    Then the client receives status code of 422
```

```json
curl -X POST "http://localhost:8080/v1/customers" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{\"accountNumber\":1009,\"name\":\"Victor\",\"balance\":-1}"
```  
```json
{
  "situation": {
    "date": "2020-12-01T23:50:16.148+00:00",
    "uri": "uri=/v1/customers",
    "codigo": 422,
    "message": "balance cannot start negative"
  }
}
```  
```
  Scenario: client makes call to POST /v1/customers
    When the client calls /v1/customers with <accountNumber> already exists and <name> and a <balance>
    Then the client receives status code of 422
```  

```json
curl -X POST "http://localhost:8080/v1/customers" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{\"accountNumber\":1001,\"name\":\"Vanessa\",\"balance\":1000}"
```  

```json
{
  "situation": {
    "date": "2020-12-01T23:54:00.393+00:00",
    "uri": "uri=/v1/customers",
    "codigo": 422,
    "message": "account number already registered"
  }
}
```

```
  Scenario Outline: client makes call to GET /v1/customers/account
    When the client calls /v1/customers/account with <accountNumber>
    Then the client receives status code of 200
    And the client receives body customer
```
    
```json
curl -X GET "http://localhost:8080/v1/customers/account/1001" -H  "accept: application/json"
```

```json
{
  "result": {
    "id": 1,
    "accountNumber": 1001,
    "name": "Maria",
    "balance": 10000
  },
  "situation": {
    "date": "2020-12-01T23:56:39.282+00:00",
    "codigo": 200,
    "message": "success"
  }
}
```

```
  Scenario Outline: client makes call to GET /v1/customers/account
    When the client calls /v1/customers/account with <accountNumber>
    Then the client receives status code of 404
```

```json
curl -X GET "http://localhost:8080/v1/customers/account/777" -H  "accept: application/json"
```

```json
{
  "situation": {
    "date": "2020-12-01T23:57:27.084+00:00",
    "uri": "uri=/v1/customers/account/777",
    "codigo": 404,
    "message": "account not found"
  }
}
```

```
  Scenario Outline: client makes call to POST /v1/transfers
    When the client calls /v1/transfers with a existing <originAccount> and a existing <destinationAccount> and a positive <value> under thousand
    Then the client receives status transfer code of 201
    And the client receives the registered transfer with the id
```
   
```json
curl -X POST "http://localhost:8080/v1/transfers" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{\"originAccount\":1001,\"destinationAccount\":1002,\"value\":700}"
```

```json
{
  "result": {
    "id": 5,
    "originAccount": 1001,
    "destinationAccount": 1002,
    "value": 700,
    "status": "SUCCESS",
    "date": "2020-12-01T23:59:04.442+00:00"
  },
  "situation": {
    "date": "2020-12-01T23:59:04.494+00:00",
    "codigo": 201,
    "message": "success"
  }
}
```

```
  Scenario Outline: client makes call to POST /v1/transfers
    When the client calls /v1/transfers with <originAccount> or <destinationAccount> not existing and <value>
    Then the client receives status transfer code of 404
```

```json
curl -X POST "http://localhost:8080/v1/transfers" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{\"originAccount\":777,\"destinationAccount\":1002,\"value\":700}"
```

```json
{
  "situation": {
    "date": "2020-12-02T00:01:23.145+00:00",
    "uri": "uri=/v1/transfers",
    "codigo": 404,
    "message": "account not found"
  }
}
```

```
  Scenario Outline: client makes call to POST /v1/transfers
    When the client calls /v1/transfers with <originAccount> negative balance and <destinationAccount> and <value>
    Then the client receives status transfer code of 422
```
    
```json
curl -X POST "http://localhost:8080/v1/transfers" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{\"originAccount\":1007,\"destinationAccount\":1001,\"value\":700}"
```

Response:

```json
{
  "situation": {
    "date": "2020-12-02T00:03:10.961+00:00",
    "uri": "uri=/v1/transfers",
    "codigo": 422,
    "message": "insufficient balance"
  }
}
```

```bash
  Scenario: client makes call to POST /v1/transfers
    When the client calls /v1/transfers with <originAccount> and <destinationAccount> and a negative <value>
    Then the client receives status transfer code of 422
```

```json
curl -X POST "http://localhost:8080/v1/transfers" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{\"originAccount\":1007,\"destinationAccount\":1001,\"value\":-4}"
```

```json
{
  "situation": {
    "date": "2020-12-02T00:04:12.222+00:00",
    "uri": "uri=/v1/transfers",
    "codigo": 422,
    "message": "value cannot be less than or equal to zero"
  }
}
```

```bash
  Scenario Outline: client makes call to POST /v1/transfers
    When the client calls /v1/transfers with <originAccount> and <destinationAccount> and <value> greater than
    Then the client receives status transfer code of 422
```
    
```json
curl -X POST "http://localhost:8080/v1/transfers" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{\"originAccount\":1001,\"destinationAccount\":1002,\"value\":1200}"
```

```json
{
  "situation": {
    "date": "2020-12-02T00:07:07.109+00:00",
    "uri": "uri=/v1/transfers",
    "codigo": 422,
    "message": "exceeded transfer value"
  }
}
```

```
  Scenario Outline: client makes call to GET /v1/transfers/account
    When the client calls /v1/transfers/account with a existing <accountNumber>
    Then the client receives status transfer code of 200
    And the client receives transfers list
```
    
```json
curl -X GET "http://localhost:8080/v1/transfers/account/1001" -H  "accept: application/json"
```

```json
{
  "result": [
    {
      "id": 9,
      "originAccount": 1001,
      "destinationAccount": 1002,
      "value": 1200,
      "status": "EXCEEDED_TRANSFER_VALUE",
      "date": "2020-12-02T00:07:07.106+00:00"
    },
    {
      "id": 8,
      "originAccount": 1007,
      "destinationAccount": 1001,
      "value": -4,
      "status": "NEGATIVE_TRANSFER_VALUE",
      "date": "2020-12-02T00:04:12.219+00:00"
    },
    {
      "id": 7,
      "originAccount": 1007,
      "destinationAccount": 1001,
      "value": 700,
      "status": "INSUFFICIENT_BALANCE",
      "date": "2020-12-02T00:03:10.956+00:00"
    },
    {
      "id": 5,
      "originAccount": 1001,
      "destinationAccount": 1002,
      "value": 700,
      "status": "SUCCESS",
      "date": "2020-12-01T23:59:04.442+00:00"
    },
    {
      "id": 3,
      "originAccount": 1002,
      "destinationAccount": 1001,
      "value": 30000,
      "status": "INSUFFICIENT_BALANCE",
      "date": "2020-11-24T04:00:00.000+00:00"
    },
    {
      "id": 4,
      "originAccount": 1001,
      "destinationAccount": 1002,
      "value": 1200,
      "status": "EXCEEDED_TRANSFER_VALUE",
      "date": "2020-11-23T10:00:00.000+00:00"
    },
    {
      "id": 1,
      "originAccount": 1002,
      "destinationAccount": 1001,
      "value": 10,
      "status": "SUCCESS",
      "date": "2020-11-23T04:00:00.000+00:00"
    },
    {
      "id": 2,
      "originAccount": 1003,
      "destinationAccount": 1001,
      "value": 10,
      "status": "ACCOUNT_NOT_EXISTS",
      "date": "2020-11-22T10:00:00.000+00:00"
    }
  ],
  "situation": {
    "date": "2020-12-02T00:08:18.284+00:00",
    "codigo": 200,
    "message": "success"
  }
}
```

