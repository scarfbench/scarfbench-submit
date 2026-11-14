# Jakarta EE Bill Payment Application

This Jakarta EE 10 application demonstrates CDI dependency injection, qualified
events, interceptor bindings, Servlet-based HTTP handling, and Bean Validation
compatible input constraints.

## Build and run

Prerequisites are JDK 17 and Maven 3.9 or the included Maven wrapper.

```bash
./mvnw clean verify
./mvnw liberty:run
```

Open `http://localhost:8080/billpayment`. The application is packaged as
`target/billpayment.war` and deployed by Open Liberty at `/billpayment`.

Submitting `/pay` fires either a `@Debit` or `@Credit` CDI event. The qualified
observer logs the corresponding payment, while the `@Logged` Jakarta interceptor
logs intercepted business calls. `/reset` returns the form to its defaults and
`/css/default.css` retains the application's stylesheet route.
