# Credit Module Challenge

The project is created just for experimenting and learning features of Java and Spring Boot.

## Prerequisites
- JDK >= 21
- JAVA_HOME environment variable is set
- Postgres >= 17

There is a convenience compose.yaml file to deploy dependencies for development purpose such as Postgres SQL Database.
Run the following command on the root directory.
```
docker compose up -d
```

## Spring Boot Modules
This project consist of several modules which is independently managed Spring Boot projects which use Gradle build system.

| Module&nbsp;Name | Default port | Description                                                         |
|:----------------:|:------------:|---------------------------------------------------------------------|
|  config-server   |     8888     | Config server which needs to boot before other modules              |
|     loan-api     |     8100     | Loan API which contains business logic of customers and their loans |

## How to build
There are several ways to build Spring Boot modules.
The traditional way is to use embedded gradlew
scripts which use gradle build system.
Depending on platform, you can run the following commands.
Please make sure you are on the root directory of project.

### Windows

```
.\gradlew.bat --no-daemon -p config-server clean build
.\gradlew.bat --no-daemon -p loan-api clean build
```

### Linux or macOS

```
sh gradlew --no-daemon -p config-server clean build
sh gradlew --no-daemon -p loan-api clean build
```

### Docker
It is also possible to build Docker images by using following commands respectively.

- You can pull necessary images below before starting.
  ```
  docker pull gradle:jdk21-alpine
  docker pull eclipse-temurin:21-alpine
  docker pull postgres:17.2
  ```

- Build Spring Boot JAR files using compose-gradle.yaml file.
  ```
  docker compose -f compose-gradle.yaml up
  ```

- Build Docker images using compose-local.yaml file.
  Environment variables are for versioning of images.
  ```
  CONFIG_SERVER_VERSION=SNAPSHOT LOAN_API_VERSION=SNAPSHOT docker compose -f compose-local.yaml build
  ```

## How to run
There are several ways to run modules. Please make sure you are on the root directory of project.

### Traditional way
The traditional way to run by following commands.
```
java -jar config-server/build/libs/config-server-SNAPSHOT.jar --spring.profiles.active=local,native
java -jar loan-api/build/libs/loan-api-SNAPSHOT.jar --spring.profiles.active=local
```

### Gradle way
Gradle bootRun task can be used to run.

#### Windows
```
.\gradlew.bat --no-daemon -p config-server bootRun --args='--spring.profiles.active=local,native --spring.cloud.config.server.native.search-locations=file:../config/{application}'
.\gradlew.bat --no-daemon -p loan-api bootRun --args='--spring.profiles.active=local'
```

#### Linux or macOS
```
sh gradlew --no-daemon -p config-server bootRun --args='--spring.profiles.active=local,native --spring.cloud.config.server.native.search-locations=file:../config/{application}'
sh gradlew --no-daemon -p loan-api bootRun --args='--spring.profiles.active=local'
```

### Docker way
You can also run entire stack in Docker by using the command below.
```
CONFIG_SERVER_VERSION=SNAPSHOT LOAN_API_VERSION=SNAPSHOT docker compose -f compose-local.yaml up -d
```

## Test

- There are 4 endpoints in loan-api.

  | Method | Endpoint                      | Headers                               | Description                                    |
  |:-------|:------------------------------|---------------------------------------|:-----------------------------------------------|
  | POST   | /customer-loans               | `X-Customer-Id`                       | Used to create new customer loans.             |
  | GET    | /customer-loans               | `X-Customer-Id`                       | Used to list customer loans.                   |
  | GET    | /customer-loans/installments  | `X-Customer-Id`, `X-Customer-Loan-Id` | Used to list installments of a customer loan.  |
  | POST   | /customer-loans/pay           | `X-Customer-Id`, `X-Customer-Loan-Id` | Used to pay a customer loan.                   |

- Swagger UI is available at `/swagger-ui/index.html`
- API can be tested using the Postman collection in `postman` folder.

## Development
Any modern IDE will be useful for development of this project.
However, it is also possible to use a text editor or a terminal. Before running Spring Boot apps, please set appropriate properties below.

Before starting config-server in your local, you can use native and local profiles.
```
spring.profiles.active=native,local
```

You can also change the location of config directory.
```
spring.cloud.config.server.native.search-locations
```

For other modules, you can use local profile.
```
spring.profiles.active=local
```

## Roadmap
1. - [ ] API Gateway Integration

2. - [ ] JWT Authorization
     - [ ] Setup authorization server
     - [ ] Create user domain
     - [ ] Configure resource server
     - [ ] Role based access

  