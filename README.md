# Credit Module Challenge

The project is created just for experimenting and learning features of Java and Spring Boot.

## Prerequisites
- JDK >= 21
- JAVA_HOME environment variable is set
- Postgres >= 17

There is convenience compose.yaml file to deploy dependencies for development purpose such as Postgres SQL Database.
Run the following command on the root directory.
```
docker compose up -d
```

## Spring Boot Modules
This project consist of several modules which is independently managed Spring Boot projects which use Gradle build system.

| Module&nbsp;Name | Description                                                         |
|:----------------:|---------------------------------------------------------------------|
|  config-server   | Config server which needs to boot before other modules              |
|     loan-api     | Loan API which contains business logic of customers and their loans |

## How to build
There are several ways to build a Spring Boot module.
The traditional way is to use embedded gradlew
scripts which use gradle build system.
Depending on platform run the following commands.

### Windows
You need to go each module directory to run separately.
```
gradlew.bat clean install
```

### Linux or macOS
You need to go each module directory to run separately.
```
gradlew clean install
```

### Docker
It is also possible to build Docker images by using following command.
Environment variables at the beginning are for versioning of images.
Please make sure you are on the root directory of project.
```
CONFIG_SERVER_VERSION=SNAPSHOT LOAN_API_VERSION=SNAPSHOT docker compose -f compose-local.yaml build
```
## How to run
There are several ways to run modules.

### Traditional way
The traditional way to run by following command. You need to go each module directory to run separately.
```
java -jar build/libs/$JAR_FILE_NAME.jar
```

### Gradle way
Gradle bootRun task can be used to run. You need to go each module directory to run separately.
#### Windows
```
gradlew.bat bootRun
```

#### Linux or macOS
```
gradlew bootRun
```

### Docker way
You can also run entire stack in docker by using the command below. Please make sure you are on the root directory of project.
```
CONFIG_SERVER_VERSION=SNAPSHOT LOAN_API_VERSION=SNAPSHOT docker compose -f compose-local.yaml up -d
```

## Development
Any modern IDE will be useful for development of this project.
However, it is also possible to use a text editor or a terminal. Before running Spring Boot apps, please set appropriate profile in JVM options.

For example:
```
-Dspring.profiles.active=local
```

