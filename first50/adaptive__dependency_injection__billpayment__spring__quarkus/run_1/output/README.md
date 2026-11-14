# Quarkus Bill Payment Application

This Quarkus application demonstrates CDI injection, qualified CDI events, and Jakarta interceptors while preserving the bill payment web interface.

## Build and run

Use Java 17 and the Maven wrapper:

```bash
./mvnw clean verify
java -jar target/quarkus-app/quarkus-run.jar
```

The application is available at `http://localhost:8080/billpayment`.

The root page accepts debit and credit payments. `/pay` processes the form, `/reset` restores its defaults, and `/css/default.css` serves the stylesheet. Payment events use separate CDI qualifiers for debit and credit observers, and methods marked with `@Logged` are handled by a Jakarta `@AroundInvoke` interceptor.
