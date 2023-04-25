# Medical Laboratory Service

## 1. Description

Mediacal Laboratory Service is a **RESTful** application that allows patients to access their laboratory test results online. 
The application stores user personal data as well as diagnostic results, enabling users to view and download their results anytime, anywhere.
The application is intended to be used by medical laboratories.

**Please note that the application is still in development and is not yet complete.**

## 2. Technnologies

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

Additional frameworks and technologies may be added during further development.

## 3. How to run

To run the application, you need to have `Postgres` installed. You can create a database named `medical`, or use a different one by updating the configuration in `application.yml`. Make sure to also check the username and password for the database.

To start the application, you can either run the `MedicalLaboratoryApplication` class, or type `./mvnw spring-boot:run` in your IDE terminal.

Once the application is running, you can test it by sending requests to the endpoints using tools like Postman. The application runs on `http://localhost:8080/`

Keep in mind that the application is secured with JWT token. To access the endpoints, you need to either register a new user at `http://localhost:8080/medical/auth/register`, or use the provided test user with user privileges (login: `testuser`, password: `testpassword`) or the test admin user (login: `testadmin`, password: `testpassword`).

## 4. Future plans for the application

Next, I plan to add user and admin views to the application using HTML, CSS, and JavaScript.
