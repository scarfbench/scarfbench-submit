# billpayment

This application is a Jakarta EE 10 web application deployed to Open Liberty.
It preserves the bill-payment Jakarta Faces flow, CDI session state, qualified
events, Bean Validation, and interceptor logging under `/billpayment`.

## Build

Use the Maven wrapper with JDK 17:

```shell
./mvnw clean package
```

The deployable artifact is `target/billpayment.war`.

## Run

Start the managed server with:

```shell
./mvnw liberty:run
```

Then open <http://localhost:8080/billpayment>.

The HTTP port can be changed with `-Dhttp.port=9080`.
