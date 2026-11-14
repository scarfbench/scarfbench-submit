# standalone

Jakarta EE 10 standalone example deployed as a WAR on Open Liberty.

Build and run the supplied tests with:

```shell
./mvnw clean verify
```

Start the managed server with:

```shell
./mvnw liberty:run
```

The application is available at `http://localhost:9080/standalone`, and the
JSON greeting endpoint is `http://localhost:9080/standalone/greet`.
