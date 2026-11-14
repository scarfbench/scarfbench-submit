# converter

This application uses Spring Boot and preserves the converter endpoint at
`http://localhost:8080/converter`.

## Run in development

```shell
./mvnw spring-boot:run
```

## Package and run

```shell
./mvnw clean package
java -jar target/converter.jar
```

The form accepts a dollar amount, converts it to yen, and then converts that
yen amount to euros using the original rates and upward two-decimal rounding.

## Native executable

With GraalVM installed, build the executable used by the native Dockerfiles:

```shell
./mvnw -Pnative native:compile
```
