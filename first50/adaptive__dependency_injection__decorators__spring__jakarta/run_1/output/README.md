# Jakarta EE Decorators Application

This application demonstrates Jakarta CDI dependency injection, decorators, and interceptors on Open Liberty.

## Original vs Spring Boot Conversion

TODO

## Building and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Build
```bash
mvn clean compile
```

### Run on Open Liberty
```bash
./mvnw liberty:run
```

The application will be available at: http://localhost:9080/decorators

### Package
```bash
mvn clean package
```

This creates `target/decorators.war`.
