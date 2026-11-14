# decorators

This Jakarta EE 10 application demonstrates CDI decorators and interceptors with Jakarta Faces.

Build the deployable WAR and run the existing Java tests with:

```shell
./mvnw clean verify
```

Run the application on Open Liberty with:

```shell
./mvnw liberty:run
```

The application is available at <http://localhost:8080/decorators/>. The deployable artifact is
`target/decorators.war`. The HTTP session timeout remains 30 minutes.
