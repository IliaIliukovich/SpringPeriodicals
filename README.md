# Spring Periodicals
#### Spring MVC Test Project with REST API and OAuth2 Support.

The system stores a catalog of periodicals. The **administrator** maintains the catalog. The **reader** 
can subscribe by pre-selecting a periodical from the list. The system calculates the amount to be paid and registers 
the payment.


### Technologies used:
- Java 8 ([tag](https://github.com/IliaIliukovich/SpringPeriodicals/releases/tag/stable-java-8-version))
- Java 17 (latest master commits)
- Maven
- MySql and H2 database
- Spring MVC
- Spring Data JPA and Hibernate
- Spring Security
- Spring OAuth 2 support (active for Java 8 tag, disabled in current version)
- jsp
- Bootstrap

#### How to run the app: 
1. Setup MySQL DB
2. Load dump from /resources/ folder: schema.sql, data.sql files
3. mvn clean install cargo:run
4. Access URL: http://localhost:8080/SpringPeriodicals

### Roles
#### administrator:
- login: admin
- password: admin
#### reader:
- login: tom
- password: tom
