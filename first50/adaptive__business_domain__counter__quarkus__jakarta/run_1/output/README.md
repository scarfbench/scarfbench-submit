# counter

This Jakarta EE 10 application runs on Open Liberty and preserves the `/counter` context path.

## Build

```shell
./mvnw clean package
```

The deployable application is produced as `target/counter.war`.

## Run

```shell
./mvnw liberty:run
```

Open <http://localhost:8080/counter>. Each page request displays the next application-wide count, beginning at 1.

Stop a running development server with `./mvnw liberty:stop`.
