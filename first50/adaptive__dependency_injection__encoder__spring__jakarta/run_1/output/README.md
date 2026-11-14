# Jakarta EE Encoder Application

This encoder runs as a Jakarta EE 10 WAR on Open Liberty.

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
./mvnw liberty:run
```

The application will be available at: http://localhost:8080/encoder

### Package
```bash
mvn clean package
```

This creates `target/encoder.war` for deployment to a Jakarta EE Web Profile 10 server.
