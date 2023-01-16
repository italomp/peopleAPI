# PeopleAPI
### Important: This API was developed in english, so your entities and entities attributes are in english.
This API allows you to manage people and their addresses. <br>

### Base URL:
http://44.206.242.29:8080

## Features: <br>
### 1ª Create Person: <br>
**Endpoint:** /api/v1/person<br>
**HTTP method:** POST <br>
**Body example:** <br>
```
{
    "name": "Italo Modesto Pereira",
    "birthdate": "1992-12-30",
    "mainAddress": {
        "street": "Rua 1",
        "number": 10,
        "cep": 58429120,
        "city": "Campina Grande"
    },  <br>
    "alternativeAddressList": [
        { <br>
        "street": "Rua 1",
        "number": 11,
        "cep": 58429120,
        "city": "Campina Grande"
        },
        {
        "street": "Rua 1",
        "number": 12,
        "cep": 58429120,
        "city": "Campina Grande" 
        }
    ]
}
```
**Success response:** a response with HTTP status code 201. <br>
**Fail Response:** a message error if you omit the name or birthdate of the person. <br>
**Note:** You can omit the mainAddress and alternativeAddresList. <br>

### 2ª Update Person: <br>
**Endpoint:** /api/v1/person<br>
**HTTP method:** PUT <br>
**Body example:** <br>
```
{
    "id": 2,
    "name": "Italo Pereira", 
    "birthdate": "1992-12-31",
    "mainAddress": {
        "street": "Rua 1",
        "number": 12,
        "cep": 58429120, 
        "city": "Campina Grande" 
    }, 
    "alternativeAddressList": [
        {
        "street": "Rua 1",
        "number": 10,
        "cep": 58429120, 
        "city": "Campina Grande" 
        },
        {
        "street": "Rua 1",
        "number": 11,
        "cep": 58429120, 
        "city": "Campina Grande" 
        },
        {
        "street": "Rua 1",
        "number": 12,
        "cep": 58429120, 
        "city": "Campina Grande" 
        }]
}
```
**Success response:** a response with HTTP status code 200. <br>
**Fail Response:** a message error if you omit name or birthdate, or if you give a invalid id. <br>
**Note:** You can omit mainAddress and alternativeAddressList. <br>

### 3ª Get a Person: <br>
**Endpoint:** /api/v1/person/**{id}**<br>
**HTTP method:** GET <br>
**Success response:** a response with person in your body. <br>
**Fail Response:** a message error if you give a invalid id. <br>
**Note:** {id} should be replaced by person id. <br>

### 4ª Get All Person: <br>
**Endpoint:** /api/v1/person<br>
**HTTP method:** GET <br>
**Success response:** a response with all person in your body. <br>

### 5ª Create Address For a Person: <br>
**Endpoint:** /api/v1/person/**{id}**<br>
**HTTP method:** POST <br>
**Body example:** <br>
```
{ 
    "street": "Rua 1",
    "number": 100,
    "cep": 58429120, 
    "city": "Campina Grande"
}
```
**Success response:** a response with HTTP status code 201. <br>
**Fail Response:** a message error if you give a invalid id. <br>
**Note:** {id} should be replaced by person id. <br>

### 6ª Get All Addresses of Person: <br>
**Endpoint:** /api/v1/person/**{id}**/address<br>
**HTTP method:** GET <br>
**Success response:** a response with all addresses of person in your body. <br>
**Fail Response:** a response with an empty list if you give a invalid id. <br>
**Note:** {id} should be replaced by person id. <br>

### 7ª Set Main Address of a Person: <br>
**Endpoint:** /api/v1/person/**{id}**<br>
**HTTP method:** PUT <br>
**Query param:** key: address_id, value: id of the address <br>
**Success response:** a response with HTTP status code 200. <br>
**Fail Response:** a message error if you give a invalid id. <br>
