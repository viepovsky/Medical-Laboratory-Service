# Medical Laboratory Service
[![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/viepovsky/Medical-Laboratory-Service/maven.yml?style=plastic)](https://github.com/viepovsky/Medical-Laboratory-Service/actions/workflows/maven.yml)
[![Codecov test coverage](https://img.shields.io/codecov/c/github/viepovsky/Medical-Laboratory-Service?style=plastic)](https://codecov.io/gh/viepovsky/Medical-Laboratory-Service)

Medical Laboratory Service is a secure **RESTful** application that allows patients to access their laboratory test results online. 
The application's endpoints are protected using JSON Web Tokens, ensuring that only authorized users can access sensitive medical data. 
The application stores user personal data, as well as diagnostic results. Users after authorization can simply view and download their results online. 
In addition, the application allows patients to browse available examinations that are offered by the laboratory. 
This application is specifically designed to be used by medical laboratories.

## Technnologies

The application currently uses the following frameworks and technologies: 

- Spring Boot: Web, Data JPA, Test, Validation
- Endpoints secured using Spring Security with JSON Web Tokens (JWT)
- Hibernate
- Unit tests with: JUnit, Mockito, AssertJ
- PostgreSQL + H2 for unit tests
- Flyway
- Lombok
- Maven
- Swagger

In this service I have used my own PESEL validation library to ensure the correctness of PESEL numbers. The library has been published on Maven Central Repository and is accessible for everyone to use. You can check out the library's code [**here**](https://github.com/viepovsky/PESEL).

## How to run

To run the application, you need to have `Postgres` installed. You can create a database named `medical`, or use a different one by updating the configuration in `application.yml`. Make sure to also check the username and password for the database.

Before starting the application, you need to set an environment variable named `MEDICAL_SECRET_KEY` with a 256-bit hex encryption key or copy the one from `application-test.yml` and paste it into `application.yml`.

To start the application, you can either run the `MedicalLaboratoryApplication` class, or type `./mvnw spring-boot:run` in your IDE terminal.

Once the application is running, you can test it by sending requests to the endpoints using tools like Postman. The application runs on `http://localhost:8080/`

Keep in mind that the application is secured with JWT token. To access the endpoints, you need to either register a new user at `http://localhost:8080/medical/auth/register`, or use the provided test user with user privileges (login: `testuser`, password: `testpassword`) or the test admin user (login: `testadmin`, password: `testpassword`).

## Future plans for the application

Next, I plan to add user and admin views to the application using HTML, CSS, and JavaScript.

## Screenshots

![Swagger screenshot](src/main/resources/screenshots/swagger.jpg)


![Test coverage screenshot](src/main/resources/screenshots/test-coverage.jpg)
