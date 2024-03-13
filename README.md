# Eye 

#### Java backend framework

#### Version 1.0.2

## Features

- Multi-thread request
- Memory safe response
- Routes

## Todo

- fix cors

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
		new String[]{"*"},
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

### Types of response

From utilities provided by the framework

- from ram         (String)
- from system file (path)

```java
public final class HTML extends Response {
	public HTML(String content) {
		super(content, "Content-Type: text/html\r\n");
	}

	public HTML(Path path) {
		super(path, "Content-Type: text/html\r\n");
	}
}
```

### Create route

Ram response

```java
   import Eye.RequestHandler;

public class Root extends Route {
	public Root() {
		super("/");
	}

	@Override
	protected Response response() throws IOException {
		// you can access query parameters from RequestHandler
		RequestHandler request = getRequest();
		return new HTML("Hello, World!");
	}
}
```

System file response

```java
public class Root extends Route {
	public Root() {
		super("/");
	}

	@Override
	protected Response response() throws IOException {
		// you can access query parameters from RequestHandler
		RequestHandler request = getRequest();
		return new HTML(Path.of("./index.html"));
	}
}
```
