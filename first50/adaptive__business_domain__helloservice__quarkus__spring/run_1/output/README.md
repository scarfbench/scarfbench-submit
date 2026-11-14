# helloservice

This project exposes the tutorial SOAP hello service with Spring Boot and Apache CXF.

## Build and run

```shell
./mvnw clean package
java -jar target/helloservice.jar
```

The application root is `http://localhost:9080/helloservice`. The SOAP WSDL is at
`http://localhost:9080/helloservice/HelloServiceBeanService?wsdl`.
