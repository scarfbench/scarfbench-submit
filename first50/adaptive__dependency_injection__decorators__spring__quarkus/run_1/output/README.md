# Quarkus Decorators Application

This application demonstrates Quarkus CDI dependency injection, an enabled CDI decorator, and a Jakarta interceptor.

## Building and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Build
```bash
./mvnw clean verify
```

### Run
```bash
./mvnw quarkus:dev
```

The application will be available at: http://localhost:8080/decorators

### Package
```bash
./mvnw clean package
```

This creates an executable Quarkus application in `target/quarkus-app/`.
