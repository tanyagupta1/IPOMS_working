# educare_ipo_ms
IPO Management System - DESIS Ascend Educare

Added dependencies:
  * spring-boot-starter-data-jpa
  * spring-boot-starter-web
  * mysql-connector-java
  * spring-boot-starter-security


### Database Setup

Enter the following SQL commands to set up the database before running the application:

`CREATE DATABASE IPOMS_DB;`

`CREATE USER desis_ipoms_user IDENTIFIED BY 'ipoms_password'`

`GRANT all on IPOMS_DB.* TO desis_ipoms_user;`

`flush privileges;`

Tomcat server setup:
Run the IpoManagementApplication.java file and go to localhost:8090 and if sign in page is visible, application successfully ran on server.

### Basic UI for testing CRUD 

| Operation                            | URL                                                       |
|--------------------------------------|-----------------------------------------------------------|
| Add user                             | localhost:8090/addUser                                    |
| Delete user                          | localhost:8090/deleteUser/{UserId}                        |
| View all users                       | localhost:8090/getAllUsers                                |
| Add money to wallet of a user        | localhost:8090/addAmountToWallet/{UserID}/{Amount}        |
| Withdraw money from wallet of a user | localhost:8090/withdrawAmountFromWallet/{UserID}/{Amount} |
| Add bid                              | localhost:8090/makeBid                                    |
| View all bids                        | localhost:8090/getAllBids                                 |
| View user specific bids              | localhost:8090/getBids/user/{userId}                      |
| View all transactions                | localhost:8090/getAllTransactions                         |
| View user specific transactions      | localhost:8090/getAllTransactions/{UserID}                |
| Add openIPO                          | localhost:8090/addOpenIPO                                 |
| View all open IPOs                   | localhost:8090/getAllOpenIPOs                             |
| View particular IPO                  | localhost:8090/getOpenIPO/{OpenIPOId}                     |
| Delete an open IPO                   | localhost:8090/deleteOpenIPO/{OpenIPOId}                  |
| Add company                          | localhost:8090/addCompany                                 |
| View all companies                   | localhost:8090/getAllCompanies                            |
| View particular company              | localhost:8090/getCompany/{CompanyId}                     |
| Delete company                       | localhost:8090/deleteCompany/{CompanyId}                  |


#### To-dos
- Improve UI

