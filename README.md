# City-List-APP
This repo can be used to Create, Update, Read, Delete the following resources.
 - Cities
 - Users

Also, this module has Spring Security (Authentication & Authorization), AOP Logging, Docker Compose, Spring Profiling, Error Handling, Spring Swagger Documentation, HATEOAS Principle Enabled
# Technical Stack & External Dependencies
use  `mvn dependency:tree` to view more.
- Java Open JDK (17)
- spring-boot-starter-data-jpa (2.7.4)
- spring-boot-starter-web (2.7.4)
- spring-boot-starter-test (2.7.4)
- postgresql (42.5.0)
- log4j-api (2.19.0)
- log4j-core (2.19.0)
- slf4j-simple (2.0.3)
- amqp-client (5.16.0)
- opencsv (5.7.1)
- spring-boot-starter-hateoas (2.7.5)
- spring-boot-starter-aop (2.7.5)
- spring-boot-starter-actuator (2.7.5)
- springdoc-openapi-ui (1.6.12)
- spring-boot-starter-oauth2-resource-server (2.7.4)
- spring-boot-starter-security (2.7.4)

# Steps to run with docker
| Step  | Instructions                        | Description                                                                                               |
| ----- |:------------------------------------|:--------------------------------------------------------------------------------------------------------- |
| 1     | `bash docker-compose-run.sh`        | Run this bash script to initiate the app container and to connect to the network.
| 2     | Test APIs stored in  DevTestAPI.txt | You can run APIs saved in the text file with the help of VS Code REST Client. Read more: https://marketplace.visualstudio.com/items?itemName=humao.rest-client

# Dev guide
- make sure your java version is 17
