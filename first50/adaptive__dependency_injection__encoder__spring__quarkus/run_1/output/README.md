# Quarkus Encoder Application

This is a Quarkus conversion of the Encoder example. The application uses
Jakarta REST for its HTML endpoints and CDI for selecting the coder
implementation.

## Building and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Build
```bash
./mvnw clean install
```

### Run
```bash
./mvnw quarkus:dev
```

The application will be available at: http://localhost:8080/encoder

### Package
```bash
./mvnw clean package
```

This creates an executable Quarkus application in `target/quarkus-app/`. Run it
with `java -jar target/quarkus-app/quarkus-run.jar`.
