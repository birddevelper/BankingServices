## Banking Manager


![Banking Service](https://m-shaeri.ir/blog/wp-content/uploads/2022/06/BankingServices.jpg)

**Banking Services** is a sample tiny microservice application that performs banking operations via Restful Webservices.

It exposes 4 endpoint as following list :

**Account Service :**

- GET localhost:8080/api/accounts/{account number} (get account information)
- POST localhost:8080/api/accounts (open a new account, and returns the account number)


**Transaction Service :**

- GET localhost:8050/api/transactions?accountNumber={accountNumber} - Get transactions of the account
- GET localhost:8050/api/getBalanceByAccountNumber?accountNumber={accountNumber} - Get balance of the account


## How to setup
It is a docker based application. Running below command in project's directory builds 3 running containers, AccountService, TransactionService and Rabbitmq message broker containers:

```bash
docker-compose up
```
**Note** : It may take time, because it needs to download all dependencies of the project.

Following list of customers are inserted into database at application startup, You can open accounts for theme :

| Customer ID | Name | Surename |
|   :---:   |  :---:   |  :---: |
| 100   | Mostafa     | Shaeri    |
| 200     | Alex       | Tailor      |
| 300     | Mieke       | Anderson      |



## How to use
This application follows OpenAPI specification in API documentation. Thanks to SwaggerUI, you can see endpoints documentation in a graphical user interface and try their functionality and see the response. After running the containers, you can access the application links as :

- Account Service Rest webservices : http://localhost:8080/api/accounts
- SwaggerUI : http://localhost:8080/swagger-ui.html


To Test the endpoints please go to *http://localhost:8080/swagger-ui.html*. It help you to read endpoints documentation and give you an UI to try them out.


You can also check Transaction service endpoints by following links :

- Transaction Service Rest webservices : http://localhost:8050/api/transactions
- SwaggerUI : http://localhost:8050/swagger-ui.html
