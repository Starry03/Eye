# Eye

#### Java backend framework

## Version 1.0.1

## TO-DO

- Memory safe response

## Features

- Multi-thread request
- Routes
- Cors

## How to use

### Create server

```java
final int PORT = 3000;
Server server = new Server(PORT);
```

### Configure server

```java
Server.setRootPath("src/Test/");

Route[] routes = new Route[]{new Root()};
server.addRoutes(routes);

Cors cors = new Cors(
		new String[]{
				"http://localhost:3000",
				"https://localhost:3000",
				"http://127.0.0.1:3000",
				"https://127.0.0.1:3000"
		},
		new String[]{"GET", "POST"},
		new String[]{"*"}
);
server.setCors(cors);
```

### Start server

```java
Thread serverThread = new Thread(server);
serverThread.start();
```
