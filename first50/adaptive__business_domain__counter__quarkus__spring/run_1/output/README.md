# counter

This application is implemented with Spring Boot and keeps the original `/counter` context path.

Build the executable application with:

```shell
./mvnw package
```

Run it with:

```shell
java -jar target/counter-1.0.0-SNAPSHOT.jar
```

The counter page is available at <http://localhost:8080/counter>. Each page request atomically increments one application-wide counter, whose first rendered value is `1`.

For development, use:

```shell
./mvnw spring-boot:run
```
