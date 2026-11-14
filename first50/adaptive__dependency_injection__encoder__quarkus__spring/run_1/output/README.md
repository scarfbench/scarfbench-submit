# encoder

This application uses Spring Boot and Java 17.

Build and run the executable application with:

```shell
./mvnw clean package
java -jar target/encoder.jar
```

The application is available at <http://localhost:8080/encoder>. The normal coder shifts letters by the selected value. Start with `--spring.profiles.active=alternative` to select the diagnostic coder implementation.
