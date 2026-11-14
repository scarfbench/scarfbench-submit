# standalone

Spring Boot implementation of the standalone greeting example.

## Build and test

```shell
./mvnw clean verify
```

## Run

```shell
java -jar target/standalone.jar
```

The application is available at `http://localhost:8080/standalone`. The
`/standalone/greet` endpoint returns the greeting as JSON.
