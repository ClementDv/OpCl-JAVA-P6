# OpCl-JAVA-P6

A local person-to-person payment application with DB setup automatically with MYSQL via Liquibase. 
Bak end part dev.

## Prerequisites

What things you need to install the software and how to install them

- Java 14
- Maven 3.6.3
- Mysql 8.0.23

## Run API

Run jar : `java -jar target/paymybuddy-0.0.1-SNAPSHOT.jar`.  
Default Controller URL `http://localhost:8080/`.   
You can change port in `src/main/resources/application.properties`

DB local server port `3306`

## Endpoints

###  Authentication

**POST**

- /paymybuddy/register

*Json Body example :*
```
{
    "email": "opcl@test.com",
    "password": "test"
}
```
It will register the user.
- /paymybuddy/login

*Same type and shape of body than register*.
Return Json repsonse with JWT (Json Web Token)

###  API
*You must send a valid JWT of bearer type in header*

**GET**

- /paymybuddy/informations

Return a JSON response with the id, email and balance of the current user.

- /paymybuddy/operations

Parameters :

1. limit (optional), default is 10. ex : /operations?limit=12

Return a JSON response with the list of the last operations of the current user.

**PUT**

- paymybuddy/transferMoneyToBank

*Json Body example :*
```
{
    "name": "BNP",
    "amount": "300",
    "description": "Description Here"
}
```
To transfer money from your balance to an bank account

- /paymybuddy/transferMoneyFromBank

Same Body than last.
To transfer money from your bank account to your balance.

- /paymybuddy/transferMoneyToUser
Same body with a valid user email as name.
To transfer money from your balance to an another user balance.

**The application take 5% of the User to User transaction**

**POST**

- /paymybuddy/addContact

Parameters :

1. contactEmail ex : /addContact?contactEmail=opcl2@test.com

Add a contact to current user.

## Documentation SWAGGER

You could have access to the API documentation by running it and go on the following link : http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/ .

---------------------------------------
*Thank You.  
CLemDv*
