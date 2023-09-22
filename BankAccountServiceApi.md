# BANK ACCOUNT SERVICE REST API DOCUMENTATION

## 1. Get the bank account by account name.

### HTTP GET ...api/v1/account/{bankAccountName}

**Summary:**  
Returns the bank account info by account name.

**Description:**

This API receives bankAccountName as the url query attribute. Returns the bank account info.

### Request:

| Parameter | In | Type  | Required | Description                 |
|-----------|----|-------|----------|-----------------------------|
| bankAccountName | url query attribute | string | true | Bank account name attribute |

**Request Example:**

GET: localhost:8080/api/v1/account/reyzis

### Responses:

**200 OK**

If the client sends an valid request, 200 HTTP OK and bank account as HTTP response should be returned.

### Bank Account Response

| Parameter     | Type    | Required | Description                    |
|---------------|---------|----------|--------------------------------|
| bankAccountName   | string  | true     | Bank account name attribute    |
| bankAccountBalance | decimal | true     | Bank account balance attribute |

**Response Example:**

```json
{
  "data": {
    "bankAccountName": "reyzis1",
    "bankAccountBalance": 0.00
  },
  "message": null,
  "description": "Bank account retrieve operation was successfully processed.",
  "httpStatusCode": 200,
  "timestamp": "2023-09-21 23:11:39"
}
```

--------------------------------------------------------------------------

## 2. Get all bank accounts.

### HTTP GET ...api/v1/account

**Summary:**  

Returns all bank accounts info.

**Description:**

This Api returns all bank accounts what available in the system.

### Request:

| Parameter | In | Type  | Required | Description                 |
|-----------|----|-------|----------|-----------------------------|


**Request Example:**

GET: localhost:8080/api/v1/account

### Responses:

**200 OK**

If the client sends an valid request, 200 HTTP OK and return all bank accounts as HTTP response.

### Bank Account Response

| Parameter     | Type    | Required | Description                    |
|---------------|---------|----------|--------------------------------|
| bankAccountName   | string  | true     | Bank account name attribute    |
| bankAccountBalance | decimal | true     | Bank account balance attribute |

**Response Example:**

```json
{
  "data": [
    {
      "bankAccountName": "reyzis1",
      "bankAccountBalance": 0.00
    },
    {
      "bankAccountName": "reyzis2",
      "bankAccountBalance": 0.00
    },
    {
      "bankAccountName": "reyzis3",
      "bankAccountBalance": 0.00
    }
  ],
  "message": null,
  "description": "Bank account retrieve operation was successfully processed.",
  "httpStatusCode": 200,
  "timestamp": "2023-09-21 23:19:37"
}
```

------------------------------------------------------------------------------

## 3. Create bank account.

### HTTP POST ...api/v1/account

**Summary:**  
Returns 200 OK as the confirmation of bank account creation.

**Description:**

This API receives a JSON object containing a bankAccountName and an bankAccountPin. Returns 200 OK as the confirmation of
bank account creation.

### Request:

| Parameter       | In   | Type      | Required | Description                    |
|-----------------|------|-----------|----------|--------------------------------|
| bankAccountName | body | string    | true | Bank account name attribute    |
| bankAccountPin  | body | string| true | The Bank account pin attribute |

**Request Example:**

POST: localhost:8080/api/v1/account

```json
{
    "bankAccountName": "reyzis1",
    "bankAccountPin": "1111"
}
```

### Responses:

**200 OK**

If the client sends an valid request, 200 HTTP OK and bank account as HTTP response should be returned.

### Bank Account Response

| Parameter     | Type    | Required | Description                    |
|---------------|---------|----------|--------------------------------|
| bankAccountName   | string  | true     | Bank account name attribute    |
| bankAccountBalance | decimal | true     | Bank account balance attribute |

**Response Example:**

```json
{
  "data": {
    "bankAccountName": "reyzis1",
    "bankAccountBalance": 0.00
  },
  "message": null,
  "description": "Bank account create operation was successfully processed.",
  "httpStatusCode": 200,
  "timestamp": "2023-09-21 23:11:39"
}
```

--------------------------------------------------------------------------

## 4. This method performs a deposit operation on a bank account

### POST .../api/v1/account/{bankAccountName}/deposit

**Summary:**  
Returns 200 OK as the confirmation what deposit operation was finished successfully

**Description:**

This API receives a JSON object containing a bankAccountName, bankAccountPin and amount, 
and return code 200 OK as the confirmation what deposit operation was finished successfully

### Request:

| Parameter         | In                  | Type    | Required | Description                  |
|-------------------|---------------------|---------|----------|------------------------------|
| bankAccountName | url query attribute | string  | true | Bank account name attribute  |
| bankAccountPin   | body                | string  | true | Bank account pin attribute   |
| amount     | body                | decimal | true | Amount for deposit operation |

**Request Example:**

POST localhost:8080/api/v1/account/reyzis1/deposit

```json
{
  "bankAccountPin" : "2222",
  "amount" : 2500.0

}
```

### Responses:

**200 OK**

If the client sends an valid request, 200 HTTP OK it means that the deposit operation was successful.

### Bank Account Response

| Parameter     | Type    | Required | Description                    |
|---------------|---------|----------|--------------------------------|


**Response Example:**

```json
{
  "data": null,
  "message": null,
  "description": "Bank account deposit operation was successfully processed.",
  "httpStatusCode": 200,
  "timestamp": "2023-09-21 23:11:39"
}
```

--------------------------------------------------------------------------

## 5. This method performs a withdraw operation on a bank account

### POST .../api/v1/account/{bankAccountName}/withdraw

**Summary:**  
Returns 200 OK as the confirmation what withdraw operation was finished successfully

**Description:**

This API receives a JSON object containing a bankAccountName, bankAccountPin and amount,
and return code 200 OK as the confirmation what withdraw operation was finished successfully

### Request:

| Parameter         | In                  | Type    | Required | Description                   |
|-------------------|---------------------|---------|----------|-------------------------------|
| bankAccountName | url query attribute | string  | true | Bank account name attribute   |
| bankAccountPin   | body                | string  | true | Bank account pin attribute    |
| amount     | body                | decimal | true | Amount for withdraw operation |

**Request Example:**

POST localhost:8080/api/v1/account/reyzis1/withdraw

```json
{
  "bankAccountPin" : "1111",
  "amount" : 500.0

}
```

### Responses:

**200 OK**

If the client sends an valid request, 200 HTTP OK it means that the withdraw operation was successful.

### Bank Account Response

| Parameter     | Type    | Required | Description                    |
|---------------|---------|----------|--------------------------------|

**Response Example:**

```json
{
  "data": null,
  "message": null,
  "description": "Bank account withdraw operation was successfully processed.",
  "httpStatusCode": 200,
  "timestamp": "2023-09-21 23:11:39"
}
```

--------------------------------------------------------------------------

## 6. This method performs a transfer operation on a bank account

### POST .../api/v1/account/transfer

**Summary:**  
Returns 200 OK as the confirmation what transfer operation was finished successfully

**Description:**

This API receives a JSON object containing a sourceBankAccountName, sourceBankAccountPin, targetBankAccountName
and amount return code 200 OK as the confirmation what transfer operation was finished successfully

### Request:

| Parameter             | In   | Type    | Required | Description                        |
|-----------------------|------|---------|----------|------------------------------------|
| sourceBankAccountName | body | string  | true | Source bank account name attribute |
| sourceBankAccountPin  | body | string  | true | Source bank account pin attribute  |
| targetBankAccountName | body | string  | true | Target bank account name attribute |
| amount                | body | decimal | true | Amount for transfer operation      |

**Request Example:**

POST localhost:8080/api/v1/account/transfer

```json
{
  "sourceBankAccountName" : "reyzis1",
  "sourceBankAccountPin" : "1111",
  "targetBankAccountName" : "reyzis2",
  "amount" : 500.0

}
```

### Responses:

**200 OK**

If the client sends an valid request, 200 HTTP OK it means that the transfer operation was successful.

### Bank Account Response

| Parameter     | Type    | Required | Description                    |
|---------------|---------|----------|--------------------------------|

**Response Example:**

```json
{
  "data": null,
  "message": null,
  "description": "Bank account transfer operation was successfully processed.",
  "httpStatusCode": 200,
  "timestamp": "2023-09-21 23:11:39"
}
```