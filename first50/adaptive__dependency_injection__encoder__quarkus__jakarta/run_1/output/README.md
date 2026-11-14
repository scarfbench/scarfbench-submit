# encoder

This application is a Jakarta EE 10 web application deployed as an `encoder.war` archive on Open Liberty.

Build and run the supplied Java tests with:

```shell
./mvnw clean install
```

Start the managed server with:

```shell
./mvnw liberty:run
```

The Jakarta Faces application is available at `http://localhost:8080/encoder/` and `http://localhost:8080/encoder/index.xhtml`. The HTTP session timeout remains 30 minutes. The normal CDI coder shifts letters by the selected value; `TestCoderImpl` remains a CDI alternative that can be enabled by a deployment descriptor when required.
