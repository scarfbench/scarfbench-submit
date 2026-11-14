# decorators

This application uses Spring Boot, Spring MVC, Thymeleaf, Jakarta Validation,
and Spring AOP. It preserves the original decorated string encoder at
`http://localhost:8080/decorators` and `/decorators/index.xhtml`.

## Build and test

Use the Maven wrapper with JDK 17:

```shell
./mvnw clean install
```

## Run

```shell
java -jar target/decorators.jar
```

The form accepts a string and a shift from 0 through 26. The base coder shifts
letters, the Spring decorator adds the descriptive result, and Spring AOP logs
calls marked with `@Logged`.
